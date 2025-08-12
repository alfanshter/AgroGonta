package com.ptpws.agrogontafarm.ui.log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.MqttClientSslConfig
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth
import com.ptpws.agrogonta.api.RetrofitClient
import com.ptpws.agrogonta.data.models.ScheduleItem
import com.ptpws.agrogonta.data.models.request.ModeRequest
import com.ptpws.agrogonta.utils.Constant
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.StandardCharsets

enum class ModeStatus { MANUAL, OTOMATIS }

class PenyiramanViewModel() : ViewModel(){

    var isLoading by mutableStateOf(true)
        private set

    var isUpdating by mutableStateOf(false)
        private set

    var selectedMode by mutableStateOf<ModeStatus?>(null)
        private set

    var isSwitchLoading by mutableStateOf(false)
        private set

    var switchState by mutableStateOf(false)
        private set

    private val api = RetrofitClient.instance

    // MQTT Config
    private val mqttBroker = "broker.hivemq.com"
    private val mqttPort = 1883
    private val mqttTopic = "myapp/switch" // ganti sesuai topic kamu
    private val topicJadwal = "AgroGonta/gh1/jadwal/gh1"

    // kontrol supaya tidak subscribe ganda
    private var subscribedSchedule = false
    private var subscribedSwitch = false

    var schedules by mutableStateOf(listOf<ScheduleItem>())
        private set

    val sslConfig = MqttClientSslConfig.builder()
        .hostnameVerifier { _, _ -> true } // Untuk testing, sebaiknya ganti dengan verifikasi aman
        .build()

    private val mqttClient: Mqtt3AsyncClient = MqttClient.builder()
        .useMqttVersion3()
        .identifier("android-client-${System.currentTimeMillis()}")
        .serverHost(Constant.brokerUri)
        .serverPort(8883)
        .sslConfig(sslConfig)
        .buildAsync()

    init {
        fetchMode()
    }


    fun fetchMode() {
        viewModelScope.launch {
            try {
                isLoading = true
                val response = api.getMode()
                if (response.isSuccessful) {
                    val mode = response.body()?.mode
                    println("API mode = $mode")
                    selectedMode = if (mode.equals("manual", true)) ModeStatus.MANUAL else ModeStatus.OTOMATIS

                    // Kalau manual, mulai koneksi MQTT
                    if (selectedMode == ModeStatus.MANUAL) {
                        connectAndSubscribeMqtt()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }


    fun updateMode(mode: ModeStatus) {
        viewModelScope.launch {
            try {
                isUpdating = true
                val response = api.setMode(ModeRequest(mode.name.lowercase()))
                if (response.isSuccessful) {
                    selectedMode = mode
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isUpdating = false
            }
        }
    }

    private fun connectAndSubscribeMqtt() {
        isSwitchLoading = true
        // Auth
        val simpleAuth = Mqtt3SimpleAuth.builder()
            .username("alfanshter")
            .password("Alfan@Dinda123".toByteArray())
            .build()

        mqttClient.connectWith()
            .simpleAuth(simpleAuth)
            .send()
            .whenComplete { _, t ->
                if (t == null) {
                    // Berhasil konek, sekarang subscribe
                    mqttClient.subscribeWith()
                        .topicFilter(Constant.penyiramanCommand)
                        .qos(MqttQos.AT_LEAST_ONCE)
                        .callback { publish ->
                            val payload = String(publish.payloadAsBytes, StandardCharsets.UTF_8).trim()
                            val value = payload.toIntOrNull()
                            switchState = (value == 1)
                            isSwitchLoading = false
                        }
                        .send()

                    // ✅ Subscribe ke jadwal otomatis
                    mqttClient.subscribeWith()
                        .topicFilter("AgroGonta/gh1/jadwal/gh1")
                        .qos(MqttQos.AT_LEAST_ONCE)
                        .callback { publish ->
                            val payload = String(publish.payloadAsBytes, StandardCharsets.UTF_8).trim()
                            parseSchedulePayload(payload)
                        }
                        .send()
                } else {
                    t.printStackTrace()
                    isSwitchLoading = false
                }
            }
    }

    fun updateSwitchState(toOn: Boolean) {
        viewModelScope.launch {
            try {
                isSwitchLoading = true
                val payload = if (toOn) "1" else "0"
                mqttClient.publishWith()
                    .topic(Constant.penyiramanCommand)
                    .payload(payload.toByteArray(StandardCharsets.UTF_8))
                    .qos(MqttQos.AT_LEAST_ONCE)
                    .retain(true)
                    .send()
                    .whenComplete { _, t ->
                        if (t != null) t.printStackTrace()
                        // switchState akan otomatis diupdate oleh callback subscribe
                        isSwitchLoading = false
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                isSwitchLoading = false
            }
        }
    }

    private fun parseSchedulePayload(payload: String) {
        try {
            if (payload.isBlank()) {
                schedules = emptyList()
                return
            }
            val jo = JSONObject(payload)
            val modeStr = jo.optString("mode", "")
            val arr = jo.optJSONArray("schedule") ?: JSONArray()
            val newList = mutableListOf<ScheduleItem>()
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                val jam = o.optString("jam", "")
                val durasi = o.optInt("durasi", 0)
                if (jam.isNotBlank()) newList.add(ScheduleItem(jam, durasi))
            }
            schedules = newList
            // (opsional) set selectedMode jika payload memberi tahu mode
            if (modeStr.equals("auto", true)) {
                selectedMode = ModeStatus.OTOMATIS
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addSchedule(jam: String, durasi: Int) {
        val updated = schedules.toMutableList()
        // mencegah duplikat jam (opsional)
        if (updated.none { it.jam == jam }) {
            updated.add(ScheduleItem(jam, durasi))
            schedules = updated
            sendScheduleToMqtt()
        }
    }

    fun deleteScheduleAt(index: Int) {
        val updated = schedules.toMutableList()
        if (index in updated.indices) {
            updated.removeAt(index)
            schedules = updated
            sendScheduleToMqtt()
        }
    }

    private fun sendScheduleToMqtt() {
        try {
            val json = JSONObject()
            json.put("mode", "auto")
            val arr = JSONArray()
            schedules.forEach {
                val o = JSONObject()
                o.put("jam", it.jam)
                o.put("durasi", it.durasi)
                arr.put(o)
            }
            json.put("schedule", arr)

            mqttClient.publishWith()
                .topic(topicJadwal)
                .payload(json.toString().toByteArray(StandardCharsets.UTF_8))
                .qos(MqttQos.AT_LEAST_ONCE)
                .retain(true) // penting: retained supaya broker simpan terakhir
                .send()
                .whenComplete { _, t ->
                    if (t != null) t.printStackTrace()
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    override fun onCleared() {
        super.onCleared()
        mqttClient.disconnect()
    }


}
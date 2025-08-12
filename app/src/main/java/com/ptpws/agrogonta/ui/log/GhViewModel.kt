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
import com.ptpws.agrogonta.data.penyiraman.DummyPenyiramanRepository
import com.ptpws.agrogonta.utils.Constant
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.data.models.GhModel
import com.ptpws.agrogontafarm.data.models.PenyiramanModel
import com.ptpws.agrogontafarm.data.penyiraman.PenyiramanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import javax.inject.Inject


class GhViewModel : ViewModel(){

    var isSwitchLoading by mutableStateOf(false)
        private set

    var switchState by mutableStateOf(false)
        private set

    var isSwitchLoadingFlushing by mutableStateOf(false)
        private set
    var switchStateFlushing by mutableStateOf(false)
        private set


    private val topicPengaduk = Constant.pengadukCommand
    private val topicFlushing = Constant.flushingCommand

    val sslConfig = MqttClientSslConfig.builder()
        .hostnameVerifier { _, _ -> true }
        .build()

    private val mqttClient: Mqtt3AsyncClient = MqttClient.builder()
        .useMqttVersion3()
        .identifier("android-gh-${System.currentTimeMillis()}")
        .serverHost(Constant.brokerUri)
        .serverPort(8883)
        .sslConfig(sslConfig)
        .buildAsync()

    init {
        connectAndSubscribe()
    }

    private fun connectAndSubscribe() {
        isSwitchLoading = true
        val simpleAuth = Mqtt3SimpleAuth.builder()
            .username("alfanshter")
            .password("Alfan@Dinda123".toByteArray())
            .build()

        mqttClient.connectWith()
            .simpleAuth(simpleAuth)
            .send()
            .whenComplete { _, t ->
                if (t == null) {
                    mqttClient.subscribeWith()
                        .topicFilter(topicPengaduk)
                        .qos(MqttQos.AT_LEAST_ONCE)
                        .callback { publish ->
                            val payload = String(publish.payloadAsBytes, StandardCharsets.UTF_8).trim()
                            val value = payload.toIntOrNull()
                            switchState = (value == 1)
                            isSwitchLoading = false
                        }
                        .send()

                    // Subscribe flushing
                    mqttClient.subscribeWith()
                        .topicFilter(topicFlushing)
                        .qos(MqttQos.AT_LEAST_ONCE)
                        .callback { publish ->
                            val payload = String(publish.payloadAsBytes, StandardCharsets.UTF_8).trim()
                            val value = payload.toIntOrNull()
                            switchStateFlushing = (value == 1)
                            isSwitchLoadingFlushing = false
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
                    .topic(topicPengaduk)
                    .payload(payload.toByteArray(StandardCharsets.UTF_8))
                    .qos(MqttQos.AT_LEAST_ONCE)
                    .retain(true) // ✅ retained agar state terakhir tersimpan
                    .send()
                    .whenComplete { _, t ->
                        if (t != null) t.printStackTrace()
                        isSwitchLoading = false
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                isSwitchLoading = false
            }
        }
    }

    fun updateSwitchStateFlushing(toOn: Boolean) {
        viewModelScope.launch {
            try {
                isSwitchLoadingFlushing = true
                val payload = if (toOn) "1" else "0"
                mqttClient.publishWith()
                    .topic(topicFlushing)
                    .payload(payload.toByteArray(StandardCharsets.UTF_8))
                    .qos(MqttQos.AT_LEAST_ONCE)
                    .retain(true)
                    .send()
                    .whenComplete { _, t ->
                        if (t != null) t.printStackTrace()
                        isSwitchLoadingFlushing = false
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                isSwitchLoadingFlushing = false
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        mqttClient.disconnect()
    }

}
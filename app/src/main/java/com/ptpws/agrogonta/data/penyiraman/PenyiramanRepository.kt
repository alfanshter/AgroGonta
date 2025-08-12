package com.ptpws.agrogontafarm.data.penyiraman

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.MqttClientSslConfig
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth
import com.ptpws.agrogonta.utils.Constant
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.data.models.GhModel
import com.ptpws.agrogontafarm.data.models.PenyiramanAutoModel
import com.ptpws.agrogontafarm.data.models.PenyiramanModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume

open class PenyiramanRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val _penyiraman = MutableStateFlow(PenyiramanModel())
    open val penyiraman: StateFlow<PenyiramanModel> get() = _penyiraman

    val _gh = MutableStateFlow(GhModel())
    open val gh: StateFlow<GhModel> get() = _gh

    val _modePenyiraman = MutableStateFlow("")
    open val modePenyiraman: StateFlow<String> get() = _modePenyiraman


    private val _lastMessage = MutableStateFlow<String?>(null)
    val lastMessage: StateFlow<String?> get() = _lastMessage

    private val _lastSchedule = MutableStateFlow<String?>(null)
    val lastSchedule: StateFlow<String?> get() = _lastSchedule


    val sslConfig = MqttClientSslConfig.builder()
        .hostnameVerifier { _, _ -> true } // Untuk testing, sebaiknya ganti dengan verifikasi aman
        .build()

    private val mqttClient: Mqtt3AsyncClient by lazy {
        MqttClient.builder()
            .useMqttVersion3()
            .identifier("android-client-${System.currentTimeMillis()}")
            .serverHost(Constant.brokerUri)
            .serverPort(8883)
            .sslConfig(sslConfig)
            .buildAsync()
    }

    fun connect() {
        // Auth
        val simpleAuth = Mqtt3SimpleAuth.builder()
            .username("alfanshter")
            .password("Alfan@Dinda123".toByteArray())
            .build()

        mqttClient.connectWith()
            .simpleAuth(simpleAuth)
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    println("❌ MQTT connect error: ${throwable.message}")
                } else {
                    println("✅ Connected to broker")
                    subscribe(Constant.penyiramanCommand)
                    subscribe(Constant.jadwalCommand)
                }
            }
    }

    private fun subscribe(topic: String) {
        mqttClient.subscribeWith()
            .topicFilter(topic)
            .qos(MqttQos.AT_LEAST_ONCE)
            .callback { publish ->
                val payloadString = publish.payloadAsBytes?.decodeToString() ?: ""
                println("📩 [$topic]: $payloadString")

                when (topic) {
                    Constant.penyiramanCommand -> _lastMessage.value = payloadString
                    Constant.jadwalCommand -> _lastSchedule.value = payloadString
                }
            }
            .send()
    }


    open suspend fun sendManualCommand(command: String): Resource<Unit> {
        return try {
            // Contoh implementasi dengan suspendCoroutine supaya bisa await hasil publish
            suspendCancellableCoroutine<Resource<Unit>> { cont ->
                mqttClient.publishWith()
                    .topic(Constant.penyiramanCommand)
                    .qos(MqttQos.AT_LEAST_ONCE)
                    .payload(command.toByteArray())
                    .send()
                    .whenComplete { _ , throwable ->
                        if (throwable != null) {
                            println("gagal ${throwable.message}")
                            cont.resume(Resource.Error("Failed to send command: ${throwable.message}"))
                        } else {
                            println("berhasil")
                            cont.resume(Resource.Success(Unit))
                        }
                    }
            }
        } catch (e: Exception) {
            println("gagal ${e.message}")
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    fun sendSchedule(schedule: String) {
        mqttClient.publishWith()
            .topic(Constant.jadwalCommand)
            .qos(MqttQos.AT_LEAST_ONCE)
            .payload(schedule.toByteArray())
            .send()
    }


    open suspend fun setPenyiraman(data: String, nilai: Int): Resource<Unit> {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("penyiraman").child(data)

        //set 0 all selenoid
        val saluranRef = db.reference.child("AgroGonta").child("gh1").child("saluran")
        val penyiramanRef = db.reference.child("AgroGonta").child("gh1").child("penyiraman")
        val targetRef = penyiramanRef.child(data)


        return try {
            when (data) {
                "baris1" -> penyiramanRef.child("baris2").setValue(0).await()
                "baris2" -> penyiramanRef.child("baris1").setValue(0).await()
            }
            saluranRef.child("tandon_a").setValue(0).await()
            saluranRef.child("tandon_b").setValue(0).await()

            targetRef.setValue(nilai).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    open suspend fun setFlushing(data: String, nilai: Int): Resource<Unit> {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("flushing").child(data)

        //set 0 all selenoid
        val saluranRef = db.reference.child("AgroGonta").child("gh1").child("saluran")
        val penyiramanRef = db.reference.child("AgroGonta").child("gh1").child("penyiraman")
        val flushingRef = db.reference.child("AgroGonta").child("gh1").child("flushing")
        val targetRef = flushingRef.child(data)


        return try {
            when (data) {
                "baris1" -> flushingRef.child("baris2").setValue(0).await()
                "baris2" -> flushingRef.child("baris1").setValue(0).await()
            }
            //turn off saluran pengisian tandon
            saluranRef.child("tandon_a").setValue(0).await()
            saluranRef.child("tandon_b").setValue(0).await()
            //turn off saluran penyiraman
            penyiramanRef.child("baris1").setValue(0).await()
            penyiramanRef.child("baris2").setValue(0).await()

            targetRef.setValue(nilai).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    open suspend fun getGh() {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1")
        val data = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val gh = snapshot.getValue(GhModel::class.java)
                gh?.let {
                    runBlocking {
                        Log.d("dinda", "onDataChange runblocking: $it ")
                        _gh.emit(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error appropriately
                println("Database error: ${error.message}")

            }

        })


    }

    open suspend fun setPengaduk(data: String, nilai: Int): Resource<Unit> {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("pengaduk").child(data)


        return try {
            val result = ref.setValue(nilai).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    open suspend fun setSaluran(data: String, nilai: Int): Resource<Unit> {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        //set 0 all selenoid
        val saluranRef = db.reference.child("AgroGonta").child("gh1").child("saluran")
        val penyiramanRef = db.reference.child("AgroGonta").child("gh1").child("penyiraman")
        val targetRef = saluranRef.child(data)

        return try {
            when (data) {
                "tandon_a" -> saluranRef.child("tandon_b").setValue(0).await()
                "tandon_b" -> saluranRef.child("tandon_a").setValue(0).await()
            }
            penyiramanRef.child("baris1").setValue(0).await()
            penyiramanRef.child("baris2").setValue(0).await()

            targetRef.setValue(nilai).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    open suspend fun updateStatus(status: Boolean, id: String): Resource<Unit> {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref =
            db.reference.child("AgroGonta").child("gh1").child("penyiraman_otomatis").child(id)
                .child("status")


        return try {
            val result = ref.setValue(status).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    open suspend fun setPenyiramanOtomatis(
        waktu: Long,
        durasi: Long,
        status: Boolean
    ): Resource<String>? {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("penyiraman_otomatis")

        return try {
            // Generate a new key
            val key = ref.push().key ?: return Resource.Error("Failed to generate key")

            val sendData = hashMapOf(
                "waktu" to waktu,
                "durasi" to durasi,
                "status" to status,
            )

            val result = ref.child(key).setValue(sendData).await()
            Resource.Success(key)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    open suspend fun setMode(status: String): Resource<Unit>? {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("mode_penyiraman")

        return try {
            val result = ref.setValue(status).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    open suspend fun getPenyiramanOtomatis(): Resource<List<PenyiramanAutoModel>> {
        val db = FirebaseDatabase.getInstance(
            "https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/"
        )
        val ref = db.reference.child("AgroGonta").child("gh1").child("penyiraman_otomatis")

        return suspendCancellableCoroutine { continuation ->
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val result = mutableListOf<PenyiramanAutoModel>()

                    for (childSnapshot in snapshot.children) {
                        val data = childSnapshot.getValue(PenyiramanAutoModel::class.java)
                        val id = childSnapshot.key
                        Log.d("dinda", "tes id : $id ")
                        if (data != null && id != null) {
                            result.add(data.copy(id = id))
                        }
                    }

                    if (result.isNotEmpty()) {
                        continuation.resume(Resource.Success(result))
                    } else {
                        continuation.resume(Resource.Error("Data tidak ditemukan atau kosong"))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resume(Resource.Error(error.message))
                }
            })
        }
    }


    open suspend fun deletePenyiramanOtomatis(id: String): Resource<Unit>? {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref =
            db.reference.child("AgroGonta").child("gh1").child("penyiraman_otomatis").child(id)

        return try {
            val result = ref.removeValue().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}
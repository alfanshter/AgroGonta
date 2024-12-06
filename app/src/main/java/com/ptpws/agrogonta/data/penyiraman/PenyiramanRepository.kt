package com.ptpws.agrogontafarm.data.penyiraman

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ptpws.agrogonta.ui.log.penyiraman.convertTimestamp
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.data.models.GhModel
import com.ptpws.agrogontafarm.data.models.PenyiramanAutoModel
import com.ptpws.agrogontafarm.data.models.PenyiramanModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.coroutines.resume

open class PenyiramanRepository @Inject constructor() {
    val _penyiraman = MutableStateFlow(PenyiramanModel())
    open val penyiraman: StateFlow<PenyiramanModel> get() = _penyiraman

    val _gh = MutableStateFlow(GhModel())
    open val gh: StateFlow<GhModel> get() = _gh

    val _modePenyiraman = MutableStateFlow("")
    open val modePenyiraman: StateFlow<String> get() = _modePenyiraman

    open suspend fun getPenyiraman() {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("penyiraman")
        val data = ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val penyiraman = snapshot.getValue(PenyiramanModel::class.java)
                penyiraman?.let {
                    runBlocking {
                        Log.d("dinda", "onDataChange runblocking: $it ")
                        _penyiraman.emit(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error appropriately
                println("Database error: ${error.message}")

            }

        })


    }



    open suspend fun setPenyiraman(data: String, nilai: Int) : Resource<Unit> {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("penyiraman").child(data)


        return try {
            val result = ref.setValue(nilai).await()
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

    open suspend fun setPengaduk(data: String, nilai: Int) : Resource<Unit> {
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


    open suspend fun setSaluran(data: String, nilai: Int) : Resource<Unit> {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("saluran").child(data)


        return try {
            val result = ref.setValue(nilai).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    open suspend fun updateStatus(status : Boolean,id : String) : Resource<Unit> {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("penyiraman_otomatis").child(id).child("status")


        return try {
            val result = ref.setValue(status).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    open  suspend fun setPenyiramanOtomatis(waktu : Long, durasi : Long, status : Boolean): Resource<String>? {
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


    open suspend fun getMode(): Resource<String> {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("mode_penyiraman")

        return try {
            val mode = ref.get().await() // Gunakan `.await()` untuk Firebase Tasks
            val modePenyiraman = mode.value as? String // Pastikan data adalah String
            if (modePenyiraman != null) {
                Resource.Success(modePenyiraman)
            } else {
                Resource.Error("Mode penyiraman tidak ditemukan")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Terjadi kesalahan")
        }
    }

    open  suspend fun setMode(status : String): Resource<Unit>? {
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

                    for (childSnapshot in snapshot.children){
                        val data = childSnapshot.getValue(PenyiramanAutoModel::class.java)
                        val id = childSnapshot.key
                        Log.d("dinda", "tes id : $id ")
                        if (data!=null && id!=null){
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


    open  suspend fun deletePenyiramanOtomatis(id : String): Resource<Unit>? {
        val db =
            FirebaseDatabase.getInstance("https://smartfarming-greenhouse-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val ref = db.reference.child("AgroGonta").child("gh1").child("penyiraman_otomatis").child(id)

        return try {
            val result = ref.removeValue().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}
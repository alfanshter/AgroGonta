package com.ptpws.agrogonta.ui.log.penyiraman

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.core.rememberDialogState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ptpws.agrogonta.data.penyiraman.DummyPenyiramanRepository
import com.ptpws.agrogonta.ui.common.CustomTimePickerDialog
import com.ptpws.agrogonta.ui.common.ErrorDialog
import com.ptpws.agrogontafarm.R
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.data.models.PenyiramanAutoModel
import com.ptpws.agrogontafarm.ui.common.LoadingDialog
import com.ptpws.agrogontafarm.ui.common.poppinsFamily
import com.ptpws.agrogontafarm.ui.log.GhViewModel
import com.ptpws.agrogontafarm.ui.log.PenyiramanViewModel
import com.ptpws.agrogontafarm.ui.log.formatTime
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PenyiramanScreen(penyiramanViewModel: PenyiramanViewModel) {


    val penyiraman by penyiramanViewModel.gh.collectAsState()

    var elapsedTime2 by remember { mutableStateOf(0L) }
    var checked2 by remember { mutableStateOf(penyiraman.penyiramanModel.baris2 == 1) }
    val scope = rememberCoroutineScope()

    // Content of your ModalBottomSheet

    val context = LocalContext.current
    var mode by remember { mutableStateOf("default") }
    var setmode by remember { mutableStateOf("default") }
    var isKranOn by remember { mutableStateOf(false) }
    var isKran2On by remember { mutableStateOf(false) }
    var schedules = remember { mutableStateListOf<PenyiramanAutoModel>() }

    // State untuk menyimpan waktu sementara
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var minuteTime by remember { mutableStateOf("") }
    var secondTime by remember { mutableStateOf("") }
    var editData by remember { mutableStateOf(false) }
    var deleteDataIndex by remember { mutableStateOf(-1) }
    var deleteId by remember { mutableStateOf("") }
    // TimePickerDialog untuk waktu mulai


    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("") }

    var showLoading = remember { mutableStateOf(false) }
    val penyiramanState by penyiramanViewModel.setPenyiramanOtomatis.collectAsState()
    val deletePenyiramanState by penyiramanViewModel.deletePenyiramanOtomatis.collectAsState()

    val modeResource by penyiramanViewModel.modePenyiraman.collectAsState()
    val setModeState by penyiramanViewModel.setMode.collectAsState()

    //loading
    //dialog sukses
    val dialogState = rememberDialogState()
    //dialog error
    val errorState = rememberDialogState()

    val penyiramanOtomatisState by penyiramanViewModel.penyiramanOtomatis.collectAsState()
    val isRefreshing by penyiramanViewModel.isRefreshing.collectAsState() // State untuk loading
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { penyiramanViewModel.refreshData()
        schedules.clear()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            // Sheet content
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Pengaturan Penyiraman",
                color = Color.Black,
                fontSize = 25.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))
            LaunchedEffect(setModeState) {
                if (setModeState is Resource.Success) {
                    mode = setmode
                    showLoading.value = false
                    Toast.makeText(context, "Mode penyiraman berhasil diubah", Toast.LENGTH_SHORT).show()

                }
                else if (setModeState is Resource.Loading){
                    showLoading.value = true
                }

                else if (setModeState is Resource.Error){
                    showLoading.value = false
                    dialogState.visible = true
                }
            }

            ErrorDialog(errorState, {
                errorState.visible = false
            },error = "Gagal ubah mode", text = "Terjadi kesalahan saat update mode. Coba lagi nanti.")

            LaunchedEffect(modeResource) {
                if (modeResource is Resource.Success) {
                    mode = (modeResource as Resource.Success<String>).result
                }
            }

            when(modeResource){
                is Resource.Success ->{
                    showLoading.value = false
                    // Pilihan Mode
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Log.d("dinda", "PenyiramanScreen: ${(modeResource as Resource.Success<String>).result}")
                            RadioButton(
                                selected = mode == "manual",
                                onClick = {
                                    penyiramanViewModel.setMode("manual")
                                    setmode = "manual"
                                    mode = "manual"
                                }
                            )
                            Text("Manual", modifier = Modifier.clickable {
                                penyiramanViewModel.setMode("manual")
                                setmode = "manual"
                                mode = "manual"
                            })
                        }
                        Spacer(modifier = Modifier.width(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = mode == "automatis",
                                onClick = {
                                    penyiramanViewModel.setMode("automatis")
                                    setmode = "automatis"
                                    mode = "automatis"


                                }
                            )
                            Text("Otomatis", modifier = Modifier.clickable {
                                penyiramanViewModel.setMode("automatis")
                                setmode = "automatis"
                                mode = "automatis"


                            })
                        }

                    }
                }
                is Resource.Error -> {
                    showLoading.value = false
                    dialogState.visible = true
                    ErrorDialog(errorState, {
                        errorState.visible = false
                    },error = "Gagal mendapatkan data", text = "Terjadi kesalahan saat get mode. Coba lagi nanti.")

                }
                is Resource.Loadings -> {
                    showLoading.value = true
                }
                else -> {
                    showLoading.value = false
                }
            }


            Spacer(modifier = Modifier.height(10.dp))
            //konten dinamis
            if (mode == "manual") {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Kontrol Kran Manual",
                    color = Color(0xff0C9359),
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, top = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 30.dp, end = 23.dp, top = 12.dp, bottom = 12.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Kontrol Kran 1",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {
                                penyiramanViewModel.setsiram("baris1", 1)

                            }
                        )

                        Divider(
                            thickness = 1.dp, modifier = Modifier
                                .rotate(90f)
                                .width(40.dp)
                                .padding(vertical = 16.dp)
                        )

                        //baris 1
                        Switch(
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorResource(id = R.color.white),
                                checkedTrackColor = colorResource(id = R.color.hijau),
                                uncheckedThumbColor = colorResource(id = R.color.white),
                                uncheckedTrackColor = Color.LightGray,
                            ),
                            checked = isKranOn,
                            onCheckedChange = {
                                isKranOn = it
                            },
                            thumbContent = if (isKranOn) {
                                {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(SwitchDefaults.IconSize)
                                    )
                                }
                            } else {
                                null
                            }
                        )

                    setsiram(penyiramanViewModel, isKranOn)

                    }
                }
                Spacer(modifier = Modifier.height(17.dp))
            waktuBerjalan(isKranOn, penyiramanViewModel)

//            waktuBerjalan(isKran2On, penyiramanViewModel)

            }
            else {
                Column(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { showTimePicker = true }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Hapus", modifier = Modifier.size(50.dp,50.dp))
                    }

                    LaunchedEffect(penyiramanOtomatisState) {
                        if (penyiramanOtomatisState is Resource.Success) {
                            val data = (penyiramanOtomatisState as Resource.Success).result
                            Log.d("dinda", "data penyiraman otomatis: $data")
                            data.forEach {
                                schedules.add(PenyiramanAutoModel(it.id,it.durasi,it.status,it.waktu))
                            }
                        }

                        else if (penyiramanOtomatisState is Resource.Error){
                            showLoading.value = false
                            dialogState.visible = true
                        }

                        else if (penyiramanOtomatisState is Resource.Loading){
                            showLoading.value = true
                        }
                    }

                    // Menampilkan daftar jadwal
                    LazyColumn {
                        itemsIndexed(schedules) { index, schedule ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 30.dp, end = 30.dp, top = 20.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(10.dp),
                                onClick = {
                                    deleteDataIndex = index
                                    deleteId = schedule.id
                                    editData = true
                                }
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 23.dp,
                                            top = 12.dp,
                                            bottom = 12.dp
                                        )
                                        .fillMaxWidth()
                                ) {
                                    Column {
                                        Text(
                                            text = "${convertTimestampToTimeString(schedule.waktu)}",
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontFamily = poppinsFamily,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.clickable {


                                            }
                                        )

                                        Text(
                                            text = "${convertDurationToMinutesAndSeconds(schedule.durasi)}",
                                            color = Color.Black,
                                            fontSize = 12.sp,
                                            fontFamily = poppinsFamily,
                                            fontWeight = FontWeight.Medium,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.clickable {


                                            }
                                        )


                                    }

                                    var isChecked by remember { mutableStateOf(schedule.status ?: false) }

                                    Switch(
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = colorResource(id = R.color.white),
                                            checkedTrackColor = colorResource(id = R.color.hijau),
                                            uncheckedThumbColor = colorResource(id = R.color.white),
                                            uncheckedTrackColor = Color.LightGray,
                                        ),
                                        checked = isChecked,
                                        onCheckedChange = {newCheckedState ->
                                            isChecked = newCheckedState // Update state lokal
                                            penyiramanViewModel.updateStatus(newCheckedState,schedule.id)
                                        },
                                        thumbContent = if (schedule.status == true) {
                                            {
                                                Icon(
                                                    imageVector = Icons.Filled.Check,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(SwitchDefaults.IconSize)
                                                )
                                            }
                                        } else {
                                            null
                                        }
                                    )


                                }
                            }
                            Spacer(Modifier.height(20.dp))

                        }
                    }
                    LaunchedEffect(startTime,endTime,minuteTime,secondTime) {
                        if (startTime.isNotEmpty() && endTime.isNotEmpty() && minuteTime.isNotEmpty() && secondTime.isNotEmpty()) {
                            // Konversi ke milidetik sejak tengah malam

                            val millisecondsSinceMidnight = (minuteTime.toInt() * 60 * 1000) + (secondTime.toInt() * 1000)
                            penyiramanViewModel.setPenyiramOtomatis(convertTimestamp(startTime),millisecondsSinceMidnight.toLong(),false)

                        }
                    }

                    // Menggunakan LaunchedEffect untuk melacak perubahan penyiramanState
                    LaunchedEffect(penyiramanState) {
                        // Pastikan hanya menambahkan jadwal saat statusnya berhasil
                        if (penyiramanState is Resource.Success) {
                            // Membuat jadwal baru
                            val id = (penyiramanState as Resource.Success).result
                            val newSchedule = PenyiramanAutoModel(id = id,durasi = convertTimestamp(startTime),status = false,waktu = convertTimestamp(startTime))

                            // Cek apakah jadwal sudah ada di dalam schedules
                            if (!schedules.contains(newSchedule)) {
                                schedules.add(newSchedule)
                            }

                            // Reset inputan setelah menambahkan jadwal
                            startTime = ""
                            endTime = ""
                        } else if (penyiramanState is Resource.Error) {
                            // Tangani error jika ada
                            showLoading.value = false
                            val message = (penyiramanState as Resource.Error).message
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        } else {
                            // Tanggapi status lain jika perlu
                            showLoading.value = false
                        }
                    }


                    // Delete Confirmation Dialog
                    if (editData) {
                        AlertDialog(
                            onDismissRequest = { editData = false },
                            title = { Text("Hapus Jadwal") },
                            text = { Text("Apakah Anda yakin ingin menghapus jadwal ini?") },
                            confirmButton = {
                                Button(onClick = {
                                    penyiramanViewModel.deletePenyiramOtomatis(deleteId)
                                    schedules.removeAt(deleteDataIndex)
                                    deleteDataIndex = -1
                                    editData = false

                                }) {
                                    Text("Hapus")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { editData = false }) {
                                    Text("Batal")
                                }
                            }
                        )

                    }



                }

                // Panggil CustomTimePickerDialog
                if (showTimePicker) {
                    CustomTimePickerDialog(
                        onTimeSelected = { time , endtimes,minute,second ->
                            selectedTime = "$time - $endtimes" // Tangkap waktu yang dipilih
                            startTime = time
                            endTime = endtimes
                            minuteTime = minute
                            secondTime = second
                            showTimePicker = false // Tutup dialog
                        },
                        onDismissRequest = {
                            showTimePicker = false // Tutup dialog tanpa memilih waktu
                        }
                    )
                }

            }





            if (showLoading.value) {
                LoadingDialog(showDialog = showLoading)
            }

            Spacer(modifier = Modifier.height(65.dp))


        }
    }

}


fun setsiram(penyiramanViewModel: PenyiramanViewModel, checked: Boolean) {
    penyiramanViewModel.setsiram("baris1", if (checked == true) 1 else 0)
}

fun convertTimestamp(time : String) : Long{
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault()) // Format hanya waktu
// Konversi waktu ke Date
    val date = timeFormat.parse(time)

// Hitung waktu dalam milidetik sejak tengah malam
    val millisecondsSinceMidnight = date?.time ?: 0

    return  millisecondsSinceMidnight
}

// Fungsi untuk mengonversi waktu
fun convertTimestampToTimeString(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return format.format(date)
}

// Fungsi untuk mengonversi durasi
fun convertDurationToMinutesAndSeconds(durationMillis: Long): String {
    val totalSeconds = durationMillis / 1000 // Konversi milidetik ke detik
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes menit $seconds detik"
}

@Preview
@Composable
private fun PenyiramanPrev() {
    val dummyModel = PenyiramanViewModel(DummyPenyiramanRepository())
    PenyiramanScreen(dummyModel)

}



@Composable
fun waktuBerjalan(checked: Boolean, penyiramanViewModel: PenyiramanViewModel) {

    val penyiramanState by penyiramanViewModel.setPenyiraman.collectAsState()

    var showLoading = remember { mutableStateOf(true) }
    val context = LocalContext.current

    when (penyiramanState) {

        is Resource.Loading -> {
            showLoading.value = true

        }

        is Resource.Success -> {
            showLoading.value = false

        }

        is Resource.Error -> {
            showLoading.value = false
            val message = (penyiramanState as Resource.Error).message
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        }

        else -> {
            showLoading.value = false
        }
    }


    if (showLoading.value) {
        LoadingDialog(showDialog = showLoading)
    }

    val scope = rememberCoroutineScope()
    var elapsedTime by remember { mutableStateOf(0L) }

    // Function to start the timer
    fun startTimer(timer: String) {
        scope.launch {
            var startTime = System.currentTimeMillis()
            while (checked) {
                val currentTime = System.currentTimeMillis()
                elapsedTime += (currentTime - startTime)
                startTime = currentTime
                delay(1) // Delay for 1 millisecond
            }
        }
    }

    // Function to stop the timer
    fun stopTimer() {
        scope.coroutineContext.cancelChildren()
    }

    LaunchedEffect(checked) {
        if (checked) {
            startTimer("baris1")
        } else {
            elapsedTime = 0L
            stopTimer()
        }
    }
    Text(
        text = formatTime(milliseconds = elapsedTime),
        color = Color(0xff06492C),
        fontSize = 16.sp,
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}



package com.ptpws.agrogonta.ui.log.flushing

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
fun FlushingScreen(penyiramanViewModel: PenyiramanViewModel) {


    val penyiraman by penyiramanViewModel.gh.collectAsState()


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
                text = "Pengaturan Flushing",
                color = Color.Black,
                fontSize = 25.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))


            ErrorDialog(errorState, {
                errorState.visible = false
            },error = "Gagal ubah mode", text = "Terjadi kesalahan saat update mode. Coba lagi nanti.")

            Spacer(modifier = Modifier.height(10.dp))

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Kontrol Flushing Manual",
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
                        text = "Kontrol Baris 1",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            penyiramanViewModel.setflushing("baris1", 1)

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

                    setFlushing(penyiramanViewModel, isKranOn)

                }
            }

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
                        text = "Kontrol Baris 1=2",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            penyiramanViewModel.setflushing("baris2", 1)

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
                        checked = isKran2On,
                        onCheckedChange = {
                            isKran2On = it
                        },
                        thumbContent = if (isKran2On) {
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

                    setFlushing(penyiramanViewModel, isKran2On)

                }
            }
            Spacer(modifier = Modifier.height(17.dp))
            waktuBerjalan(isKranOn, penyiramanViewModel)
            waktuBerjalan(isKran2On, penyiramanViewModel)




            if (showLoading.value) {
                LoadingDialog(showDialog = showLoading)
            }

            Spacer(modifier = Modifier.height(65.dp))


        }
    }

}


fun setFlushing(penyiramanViewModel: PenyiramanViewModel, checked: Boolean) {
    penyiramanViewModel.setflushing("baris1", if (checked == true) 1 else 0)
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
    FlushingScreen(dummyModel)

}



@Composable
fun waktuBerjalan(checked: Boolean, penyiramanViewModel: PenyiramanViewModel) {

    val flushingState by penyiramanViewModel.setFlushing.collectAsState()

    var showLoading = remember { mutableStateOf(true) }
    val context = LocalContext.current

    when (flushingState) {

        is Resource.Loading -> {
            showLoading.value = true

        }

        is Resource.Success -> {
            showLoading.value = false

        }

        is Resource.Error -> {
            showLoading.value = false
            val message = (flushingState as Resource.Error).message
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



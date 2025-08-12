package com.ptpws.agrogonta.ui.log.penyiraman

import android.app.TimePickerDialog
import android.content.Context
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.core.rememberDialogState
import com.ptpws.agrogonta.ui.common.ErrorDialog
import com.ptpws.agrogontafarm.ui.common.LoadingDialog
import com.ptpws.agrogontafarm.ui.common.poppinsFamily
import com.ptpws.agrogontafarm.ui.log.ModeStatus
import com.ptpws.agrogontafarm.ui.log.PenyiramanViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun PenyiramanScreen(penyiramanViewModel: PenyiramanViewModel) {
    val context = LocalContext.current
    var showLoading = remember { mutableStateOf(false) }
    val errorState = rememberDialogState()
    val isLoading = penyiramanViewModel.isLoading
    val isUpdating = penyiramanViewModel.isUpdating
    val selectedMode = penyiramanViewModel.selectedMode

    val isSwitchLoading = penyiramanViewModel.isSwitchLoading
    val switchState = penyiramanViewModel.switchState

    //timer penyiraman
    var elapsedTime by remember { mutableStateOf(0L) } // detik
    var timerRunning by remember { mutableStateOf(false) }
    //jadwal
    val schedules by remember { derivedStateOf { penyiramanViewModel.schedules } }
    var showTimePicker by remember { mutableStateOf(false) }

    // DIALOG DURASI
    var showDurasiDialog by remember { mutableStateOf(false) }
    var selectedJamForDurasi by remember { mutableStateOf("") }
    var durasiInput by remember { mutableStateOf("60") } // default 60 detik

    LaunchedEffect(switchState) {
        if (switchState) {
            timerRunning = true
            while (timerRunning) {
                delay(1000)
                elapsedTime += 1
            }
        } else {
            timerRunning = false
            elapsedTime = 0 // kalau mau reset ke 0
        }
    }

    fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

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

        ErrorDialog(errorState, {
            errorState.visible = false
        }, error = "Gagal ubah mode", text = "Terjadi kesalahan saat update mode. Coba lagi nanti.")


        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Pilih Mode", style = MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedMode == ModeStatus.MANUAL,
                                onClick = {
                                    if (!isUpdating && selectedMode != ModeStatus.MANUAL) {
                                        penyiramanViewModel.updateMode(ModeStatus.MANUAL)
                                    }
                                }
                            )
                            Text("Manual")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedMode == ModeStatus.OTOMATIS,
                                onClick = {
                                    if (!isUpdating && selectedMode != ModeStatus.OTOMATIS) {
                                        penyiramanViewModel.updateMode(ModeStatus.OTOMATIS)
                                    }
                                }
                            )
                            Text("Otomatis")
                        }
                    }


                    if (isUpdating) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Mengubah status...")
                        }
                    }


                }
            }

        }


        Spacer(modifier = Modifier.height(10.dp))
        //konten dinamis
        if (selectedMode == ModeStatus.MANUAL) {
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
//                            penyiramanViewModel.turnPumpOn()
                        }
                    )

                    Divider(
                        thickness = 1.dp, modifier = Modifier
                            .rotate(90f)
                            .width(40.dp)
                            .padding(vertical = 16.dp)
                    )

                    //Switch baris 1
                    if (isSwitchLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(30.dp))
                    } else {
                        Switch(
                            checked = switchState,
                            onCheckedChange = { newState ->
                                penyiramanViewModel.updateSwitchState(newState)
                            }
                        )
                    }

                }
            }


            Spacer(modifier = Modifier.height(17.dp))

            if (switchState) {
                Text(
                    text = "Waktu berjalan: ${formatTime(elapsedTime)}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        //jika mode otomatis
        else {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { showTimePicker = true }) {
                    Icon(
                        Icons.Default.AddCircle,
                        contentDescription = "Hapus",
                        modifier = Modifier.size(50.dp, 50.dp)
                    )
                }

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn {
                        itemsIndexed(schedules) { index, schedule ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(10.dp),
                                onClick = {
                                    // klik card => hapus jadwal (sesuai implementasimu)
                                    penyiramanViewModel.deleteScheduleAt(index)
                                }
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Column {
                                        Text(
                                            text = schedule.jam,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "${schedule.durasi} detik",
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }


            // TimePicker dialog -> setelah pilih jam langsung tampilkan dialog durasi
            if (showTimePicker) {
                TimePickerAutoDialog(context = context, onTimeSelected = { jamString ->
                    // simpan jam dan tampilkan dialog durasi
                    selectedJamForDurasi = jamString
                    durasiInput = "60" // default
                    showDurasiDialog = true
                    showTimePicker = false
                }, onDismiss = {
                    showTimePicker = false
                })
            }

            // Dialog input durasi (detik)
            if (showDurasiDialog) {
                AlertDialog(
                    onDismissRequest = { showDurasiDialog = false },
                    title = { Text("Masukkan durasi (detik)") },
                    text = {
                        Column {
                            Text(text = "Jam: $selectedJamForDurasi")
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = durasiInput,
                                onValueChange = { new ->
                                    // hanya angka
                                    if (new.all { it.isDigit() }) durasiInput = new
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                label = { Text("Durasi (detik)") },
                                singleLine = true
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            val durasi = durasiInput.toIntOrNull() ?: 0
                            if (selectedJamForDurasi.isNotBlank() && durasi > 0) {
                                penyiramanViewModel.addSchedule(selectedJamForDurasi, durasi)
                            }
                            // reset
                            durasiInput = "60"
                            selectedJamForDurasi = ""
                            showDurasiDialog = false
                        }) {
                            Text("Simpan")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDurasiDialog = false
                        }) { Text("Batal") }
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



@Composable
fun TimePickerAutoDialog(context: Context, onTimeSelected: (String) -> Unit, onDismiss: () -> Unit) {
    // buka native TimePickerDialog langsung
    LaunchedEffect(Unit) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(context, { _, h, m ->
            val jam = String.format(Locale.getDefault(), "%02d:%02d", h, m)
            onTimeSelected(jam)
        }, hour, minute, true).apply {
            setOnCancelListener { onDismiss() }
            show()
        }
    }
}


//@Preview
//@Composable
//private fun PenyiramanPrev() {
//    val dummyModel = PenyiramanViewModel(DummyPenyiramanRepository())
//    PenyiramanScreen(dummyModel)
//
//}




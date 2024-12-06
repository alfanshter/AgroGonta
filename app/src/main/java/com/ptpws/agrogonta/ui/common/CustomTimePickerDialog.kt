package com.ptpws.agrogonta.ui.common


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomTimePickerDialog(
    onTimeSelected: (String,String,String,String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var hour by remember { mutableStateOf(0) }
    var minute by remember { mutableStateOf(0) }
    var second by remember { mutableStateOf(0) }

    var additionalMinutes by remember { mutableStateOf(0) }
    var additionalSeconds by remember { mutableStateOf(0) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        title = { Text("Pilih Waktu") },
        text = {
            Column {
                // Waktu Awal
                Text("Pilih Waktu Awal", style = MaterialTheme.typography.bodyLarge)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Jam (Hour)
                    ScrollableNumberPicker(
                        value = hour,
                        range = 0..23,
                        onValueChange = { hour = it },
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = ":",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    // Menit (Minute)
                    ScrollableNumberPicker(
                        value = minute,
                        range = 0..59,
                        onValueChange = { minute = it },
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = ":",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    // Detik (Second)
                    ScrollableNumberPicker(
                        value = second,
                        range = 0..59,
                        onValueChange = { second = it },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(30.dp))

                // Durasi Tambahan
                Text("Tambahkan Durasi (Menit & Detik)", style = MaterialTheme.typography.bodyLarge)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Tambahan Menit
                    ScrollableNumberPicker(
                        value = additionalMinutes,
                        range = 0..59,
                        onValueChange = { additionalMinutes = it },
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "menit",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.width(16.dp))
                    // Tambahan Detik
                    ScrollableNumberPicker(
                        value = additionalSeconds,
                        range = 0..59,
                        onValueChange = { additionalSeconds = it },
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "detik",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

            }


        },
        confirmButton = {
            Button(onClick = {
                // Format waktu awal
                val startTime = String.format("%02d:%02d:%02d", hour, minute, second)

                // Hitung waktu akhir
                val totalSeconds =
                    (hour * 3600 + minute * 60 + second) + (additionalMinutes * 60 + additionalSeconds)
                val endHour = (totalSeconds / 3600) % 24
                val endMinute = (totalSeconds / 60) % 60
                val endSecond = totalSeconds % 60
                val endTime = String.format("%02d:%02d:%02d", endHour, endMinute, endSecond)

                val minuteFix = String.format("%02d", additionalMinutes)
                val secondFix = String.format("%02d", additionalSeconds)
                onTimeSelected(startTime,endTime,minuteFix,secondFix)
                onDismissRequest()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Batal")
            }
        }
    )
}


@Composable
fun ScrollableNumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteRange = remember { range.toList() }
    val itemCount = Int.MAX_VALUE // Jumlah item "tak terbatas"
    val middleIndex = itemCount / 2 // Mulai di tengah
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = middleIndex)

    // Tinggi setiap item dan jumlah item yang terlihat
    val itemHeight = 50.dp
    val visibleItems = 5
    val halfVisibleItems = visibleItems / 2

    Box(modifier = modifier.height(itemHeight * visibleItems)) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(itemCount) { index ->
                val actualIndex = index % range.count() // Hitung indeks berdasarkan modulo
                val number = infiniteRange[actualIndex]

                val isSelected = listState.firstVisibleItemIndex + halfVisibleItems == index
                val backgroundColor =
                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    else Color.Transparent
                val textColor =
                    if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified

                Text(
                    text = number.toString().padStart(2, '0'),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(backgroundColor, shape = RoundedCornerShape(8.dp)) // Highlight
                        .padding(8.dp), // Extra padding
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Garis pembatas untuk item tengah
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .align(Alignment.Center)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        )
    }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        val visibleIndex = (listState.firstVisibleItemIndex + halfVisibleItems) % range.count()
        val selectedValue = infiniteRange[visibleIndex]
        if (selectedValue != value) {
            onValueChange(selectedValue)
        }
    }
}

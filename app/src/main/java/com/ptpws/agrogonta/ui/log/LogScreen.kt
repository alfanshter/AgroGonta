package com.ptpws.agrogontafarm.ui.log

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.agrogonta.data.penyiraman.DummyPenyiramanRepository
import com.ptpws.agrogontafarm.R
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.ui.AppScreen
import com.ptpws.agrogontafarm.ui.common.LoadingDialog
import com.ptpws.agrogontafarm.ui.common.poppinsFamily
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogScreen(ghViewModel: GhViewModel, navController: NavController) {
    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetPengaduk = remember { mutableStateOf(false) }
    val sheetSaluran = remember { mutableStateOf(false) }

    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM yyyy"))


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->


        LazyColumn(
            Modifier
                .padding(innerPadding)
                .background(color = Color(0x253BCEAC))
                .fillMaxSize()
        ) {
            item {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.imggh1),
                        contentDescription = null,
                        Modifier.fillMaxWidth()
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .padding(24.dp)
                                .height(50.dp)
                                .background(
                                    color = colorResource(id = R.color.white),
                                    shape = RoundedCornerShape(40.dp)
                                ), colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.iconback),
                                contentDescription = null,
                                alignment = Alignment.Center
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.back),
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = colorResource(
                                    id = R.color.hijau
                                ), textAlign = TextAlign.Center
                            )
                        }

                    }

                }

                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Kontrol GH 1",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 32.sp,
                        color = colorResource(
                            id = R.color.black
                        ), textAlign = TextAlign.Center
                    )

                    Text(
                        text = "$currentDate",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.DarkGray, textAlign = TextAlign.Center
                    )

                    Card(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(top = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.elevatedCardElevation(10.dp),
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                start = 22.dp,
                                end = 22.dp,
                                top = 12.dp,
                                bottom = 12.dp
                            ),
                            text = "Kontrol",
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.DarkGray, textAlign = TextAlign.Center
                        )
                    }

                    Card(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(top = 22.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.elevatedCardElevation(10.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(
                                start = 13.dp,
                                end = 13.dp,
                                top = 20.dp,
                                bottom = 20.dp
                            )
                        ) {
                            Column(Modifier.clickable {

                            }) {
                                Row(

                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()

                                ) {
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.iconwifi),
                                            contentDescription = null
                                        )
                                        Text(
                                            modifier = Modifier.padding(
                                                start = 12.dp,
                                            ),
                                            text = "Koneksi",
                                            fontFamily = poppinsFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.DarkGray, textAlign = TextAlign.Center
                                        )
                                    }

                                    Row {

                                        Text(
                                            modifier = Modifier.padding(
                                                start = 12.dp,
                                            ),
                                            text = "agrogontafarmWifi",
                                            fontFamily = poppinsFamily,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp,
                                            color = Color.DarkGray, textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))

                                        Image(
                                            painter = painterResource(id = R.drawable.iconback),
                                            contentDescription = null,
                                            modifier = Modifier.rotate(degrees = 180f)
                                        )
                                    }


                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                Divider(thickness = 1.dp)


                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            //lampu tanaman
                            Column(Modifier.clickable {
                                sheetSaluran.value = true
                            }) {
                                Row(

                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()

                                ) {
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.iconlampu),
                                            contentDescription = null
                                        )
                                        Text(
                                            modifier = Modifier.padding(
                                                start = 12.dp,
                                            ),
                                            text = "Sumber Air",
                                            fontFamily = poppinsFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.DarkGray, textAlign = TextAlign.Center
                                        )
                                    }

                                    Image(
                                        painter = painterResource(id = R.drawable.iconback),
                                        contentDescription = null,
                                        modifier = Modifier.rotate(degrees = 180f)
                                    )


                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                Divider(thickness = 1.dp)


                            }

                            //Penyiraman
                            Spacer(modifier = Modifier.height(16.dp))
                            Column(Modifier.clickable {
                                navController.navigate(AppScreen.Penyiraman.route)
                            }) {
                                Row(

                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()

                                ) {
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.icondaun),
                                            contentDescription = null
                                        )
                                        Text(
                                            modifier = Modifier.padding(
                                                start = 12.dp,
                                            ),
                                            text = "Penyiraman",
                                            fontFamily = poppinsFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.DarkGray, textAlign = TextAlign.Center
                                        )
                                    }

                                    Image(
                                        painter = painterResource(id = R.drawable.iconback),
                                        contentDescription = null,
                                        modifier = Modifier.rotate(degrees = 180f)
                                    )


                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                Divider(thickness = 1.dp)


                            }

                            //pengaduk
                            Spacer(modifier = Modifier.height(16.dp))
                            Column(Modifier.clickable {
                                sheetPengaduk.value = true

                            }) {
                                Row(

                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()

                                ) {
                                    Row {
                                        Image(
                                            painter = painterResource(id = R.drawable.iconpengaduk),
                                            contentDescription = null
                                        )
                                        Text(
                                            modifier = Modifier.padding(
                                                start = 12.dp,
                                            ),
                                            text = "Pengaduk",
                                            fontFamily = poppinsFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.DarkGray, textAlign = TextAlign.Center
                                        )
                                    }

                                    Image(
                                        painter = painterResource(id = R.drawable.iconback),
                                        contentDescription = null,
                                        modifier = Modifier.rotate(degrees = 180f)
                                    )


                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                Divider(thickness = 1.dp)


                            }
                        }

                    }
                }
            }
        }


        KontrolPenyiraman(showBottomSheet = showBottomSheet, ghViewModel)
        KontrolPengaduk(showBottomSheet = sheetPengaduk, ghViewModel)
        KontrolSaluran(showBottomSheet = sheetSaluran, ghViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KontrolPenyiraman(
    showBottomSheet: MutableState<Boolean>,
    ghViewModel: GhViewModel
) {


    val penyiraman by ghViewModel.gh.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var elapsedTime2 by remember { mutableStateOf(0L) }
    var checked2 by remember { mutableStateOf(penyiraman.penyiramanModel.baris2 == 1) }
    var checked by remember { mutableStateOf(penyiraman.penyiramanModel.baris1 == 1) }
    val scope = rememberCoroutineScope()


    // Function to start the timer
    fun startTimer2(timer: String) {
        scope.launch {
            var startTime = System.currentTimeMillis()
            while (checked2) {
                val currentTime = System.currentTimeMillis()
                elapsedTime2 += (currentTime - startTime)
                startTime = currentTime
                delay(1) // Delay for 1 millisecond
            }
        }
    }

    // Function to stop the timer
    fun stopTimer2() {
        scope.coroutineContext.cancelChildren()
    }

    // Content of your ModalBottomSheet


    // Handle switch 2 change
    LaunchedEffect(checked2) {
        ghViewModel.setsiram("baris2", if (checked2) 1 else 0)
        if (checked2) {
            startTimer2("baris2")
        } else {
            elapsedTime2 = 0L
            stopTimer2()
        }
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            ) {
                // Sheet content
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Penyiraman",
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, end = 80.dp, top = 51.dp),
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
                            text = "Baris 1",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {
                                ghViewModel.setsiram("baris1", 1)

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
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                            },
                            thumbContent = if (checked) {
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

                        setsiram(ghViewModel, checked)


                    }
                }

                Spacer(modifier = Modifier.height(17.dp))

                waktuBerjalan(checked, ghViewModel)

                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, end = 80.dp, top = 25.dp),
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
                            text = "Baris 2",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Divider(
                            thickness = 1.dp, modifier = Modifier
                                .rotate(90f)
                                .width(40.dp)
                                .padding(vertical = 16.dp)
                        )


                        Switch(
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorResource(id = R.color.white),
                                checkedTrackColor = colorResource(id = R.color.hijau),

                                uncheckedThumbColor = colorResource(id = R.color.white),
                                uncheckedTrackColor = Color.LightGray,
                            ),
                            checked = checked2,
                            onCheckedChange = {
                                checked2 = it
                            },
                            thumbContent = if (checked2) {
                                {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(SwitchDefaults.IconSize)
                                    )
                                    ghViewModel.setsiram("baris2", 1)
                                }

                            } else {
                                ghViewModel.setsiram("baris2", 0)
                                null
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(17.dp))

                waktuBerjalan(checked2, ghViewModel)


                Spacer(modifier = Modifier.height(65.dp))
                Text(
                    text = "Lihat tips budidaya",
                    color = Color(0xff0C9359),
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 57.dp),
                )

            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KontrolPengaduk(
    showBottomSheet: MutableState<Boolean>,
    ghViewModel: GhViewModel
) {


    val penyiraman by ghViewModel.gh.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    var checked by remember { mutableStateOf(false) }

    // Observe the changes in tongbesar and update the checked state
    LaunchedEffect(penyiraman.pengadukModel.tongbesar) {
        checked = penyiraman.pengadukModel.tongbesar == 1
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            ) {
                // Sheet content
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Pengaduk",
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, end = 80.dp, top = 51.dp),
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
                            text = "Tong Besar",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,

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
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                                setpengaduk(ghViewModel, checked)
                            },
                            thumbContent = if (checked) {
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

//                        setpengaduk(ghViewModel, checked)


                    }
                }

                Spacer(modifier = Modifier.height(17.dp))

                waktuBerjalan(checked, ghViewModel)

                Spacer(modifier = Modifier.height(65.dp))
                Text(
                    text = "Lihat tips budidaya",
                    color = Color(0xff0C9359),
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 57.dp),
                )

            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KontrolSaluran(
    showBottomSheet: MutableState<Boolean>,
    ghViewModel: GhViewModel
) {


    val saluran by ghViewModel.gh.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    var checked by remember { mutableStateOf(false) }

    // Observe the changes in tongbesar and update the checked state
    LaunchedEffect(saluran.saluranmodel.saluran) {
        checked = saluran.saluranmodel.saluran == 1
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            ) {
                // Sheet content
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Saluran",
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, end = 80.dp, top = 51.dp),
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
                            text = "Sumber",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,

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
                            checked = checked,
                            onCheckedChange = {
                                checked = it
                                setSaluran(ghViewModel, checked)
                            },
                            thumbContent = if (checked) {
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

//                        setpengaduk(ghViewModel, checked)


                    }
                }

                Spacer(modifier = Modifier.height(17.dp))

                waktuBerjalan(checked, ghViewModel)

                Spacer(modifier = Modifier.height(65.dp))
                Text(
                    text = "Lihat tips budidaya",
                    color = Color(0xff0C9359),
                    fontSize = 18.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 57.dp),
                )

            }
        }
    }


}


@Composable
fun waktuBerjalan(checked: Boolean, ghViewModel: GhViewModel) {

    val penyiramanState by ghViewModel.setPenyiraman.collectAsState()

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


fun setsiram(ghViewModel: GhViewModel, checked: Boolean) {
    ghViewModel.setsiram("baris1", if (checked == true) 1 else 0)
}

fun setpengaduk(ghViewModel: GhViewModel, checked: Boolean) {
    ghViewModel.setPengaduk("tongbesar", if (checked == true) 1 else 0)
}

fun setSaluran(ghViewModel: GhViewModel, checked: Boolean) {
    ghViewModel.setSaluran("tandon", if (checked == true) 1 else 0)
}

@Composable
fun formatTime(milliseconds: Long): String {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / (1000 * 60)) % 60
    val hours = (milliseconds / (1000 * 60 * 60)) % 24
    val millis = milliseconds % 1000

    return String.format("%02d:%02d.%03d", minutes, seconds, millis)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LogScreenPrev() {
    val dummyModel = GhViewModel(DummyPenyiramanRepository())
    LogScreen(ghViewModel = dummyModel, rememberNavController())
}

@Preview
@Composable
fun KontrolPenyiramanPrev() {
    val dummyModel = GhViewModel(DummyPenyiramanRepository())
    KontrolPenyiraman(ghViewModel = dummyModel, showBottomSheet = remember { mutableStateOf(true) })
}


package com.ptpws.agrogontafarm.ui.home

import android.util.Log
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.agrogonta.data.penyiraman.DummyPenyiramanRepository
import com.ptpws.agrogonta.ui.common.ErrorDialog
import com.ptpws.agrogonta.ui.home.HomeViewModel
import com.ptpws.agrogontafarm.R
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.ui.common.poppinsFamily
import com.ptpws.agrogontafarm.ui.log.GhViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navController: NavController) {
    val ghData by homeViewModel.gh.collectAsState()
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM yyyy"))

    val weatherState by homeViewModel.weatherState.collectAsState()


    LaunchedEffect(Unit) {
        homeViewModel.fetchWeather("-7.7123,112.9156")
    }
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

                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                ) {

                    Column {
                        Row(
                            Modifier
                                .padding(start = 24.dp, top = 50.dp, end = 24.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Hello, Alfan \uD83C\uDF3F",
                                color = Color.Black,
                                fontSize = 32.sp,
                                fontFamily = poppinsFamily
                            )

                            Image(
                                painter = painterResource(id = R.drawable.icon_setting),
                                contentDescription = null
                            )
                        }

                        //curosel
                        Image(
                            painter = painterResource(id = R.drawable.imgcurosel),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 25.dp)
                        )
                    }


                    // Card that overlaps the previous Box
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.elevatedCardElevation(10.dp),
                        modifier = Modifier
                            .size(width = 304.dp, height = 85.dp)
                            .align(Alignment.TopCenter) // Align the Card at the top center of the parent Box
                            .offset(y = 280.dp) // Offset the Card to overlap with the Box (adjust as needed)
                            .zIndex(1f) // Ensure the Card is drawn on top
                    ) {
                        Row(
                            Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column {

                                Text(
                                    color = Color.Black,
                                    fontSize = 21.sp,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    text = "Greenhouse 1",
                                )

                                Text(
                                    color = Color.DarkGray,
                                    fontSize = 14.sp,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Normal,
                                    text = "${currentDate}",
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.iconnext),
                                contentDescription = null
                            )

                        }

                    }

                }

                Spacer(modifier = Modifier.height(60.dp))

                //Data Suhu
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp, top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.iconsuhu),
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Medium,
                                text = "Suhu Lingkungan",
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            when (weatherState) {
                                is Resource.Error -> {
                                    val errorMessage = (weatherState as Resource.Error).message
                                    // Show error message
                                    Text(
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontFamily = poppinsFamily,
                                        fontWeight = FontWeight.Bold,
                                        text = "Koneksi Error",
                                    )

                                }

                                is Resource.Loading -> {
                                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                                }

                                is Resource.Success -> {
                                    val weatherResponse =
                                        (weatherState as Resource.Success).result.current
                                    Text(
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontFamily = poppinsFamily,
                                        fontWeight = FontWeight.Bold,
                                        text = "${weatherResponse!!.tempC}°C ~ ${weatherResponse!!.humidity} %",
                                    )
                                }

                                else -> {

                                }
                            }


                        }


                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(12.dp),
                        modifier = Modifier.width(114.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),

                            ) {
                            Text(
                                color = Color.Black,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Medium,
                                text = "Ruangan",
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.iconsuhu),
                                    contentDescription = null
                                )

                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold,
                                    text = "${ghData.suhu.suhu1}°C",
                                )

                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.iconkelembapan),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold,
                                    text = "${ghData.kelembapan.kelembapan1} %",
                                )

                            }


                        }


                    }


                }

                Spacer(modifier = Modifier.height(10.dp))



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(12.dp),
                        modifier = Modifier.width(114.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.iconwifi),
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Medium,
                                text = "Koneksi",
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                text = "Online",
                            )


                        }


                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),

                            ) {
                            Text(
                                color = Color.Black,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Medium,
                                text = "Nutrisi",
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.iconnutrisi),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold,
                                    text = "EC 1.9",
                                )

                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.icondaun),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold,
                                    text = "-",
                                )

                            }


                        }


                    }


                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(start = 18.dp, end = 18.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),

                        ) {
                        Text(
                            color = Color.Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Medium,
                            text = "Status AI",
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.icondaun),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                text = "Hari ke -8",
                            )

                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.iconjam),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                text = "h-2 Waktunya penyemprotan",
                            )

                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.iconjam),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                text = "Gunakan EC 2.0",
                            )

                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.iconjam),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                text = "Gunakan Volume Air 1,5L/Hari",
                            )

                        }


                    }


                }


            }

            item {
                val sensorDataList = listOf(
                    MySensorData(R.drawable.iconkelembapan, "Sensor 1", "30%"),
                    MySensorData(R.drawable.iconkelembapan, "Sensor 2", "10%"),
                    MySensorData(R.drawable.iconkelembapan, "Sensor 3", "70% "),
                    // Tambah hingga 10 atau lebih data
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth().height(200.dp)
                        .padding(16.dp), horizontalArrangement =
                    Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(sensorDataList.size){index ->
                        val data = sensorDataList[index]

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.elevatedCardElevation(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),

                                ) {
                                Image(
                                    painter = painterResource(id = R.drawable.iconkelembapan),
                                    contentDescription = null
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Medium,
                                    text = data.status,
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold,
                                    text = data.value,
                                )
                            }
                        }
                    }
                }
            }

        }

    }
}

@Preview
@Composable
private fun CardPrev() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.iconkelembapan),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    text = "Lembab",
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    text = "30% - KA",
                )


            }


        }

        Spacer(Modifier.width(10.dp))

    }

}

data class MySensorData(
    val iconRes: Int,
    val status: String,
    val value: String
)
//@Preview
//@Composable
//fun HomeScreenPreview() {
//    val dummyModel = HomeViewModel(DummyPenyiramanRepository())
//    HomeScreen(homeViewModel = dummyModel, rememberNavController())
//}
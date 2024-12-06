package com.ptpws.agrogontafarm.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ptpws.agrogontafarm.R
import com.ptpws.agrogontafarm.ui.common.poppinsFamily

@Composable
fun HomeScreen() {
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
                                    text = "6 Juni 2024",
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
                                painter = painterResource(id = R.drawable.iconkelembapan),
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Medium,
                                text = "Kelembapan",
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                text = "16%",
                            )


                        }


                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(12.dp),
                        modifier = Modifier.width(114.dp)
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
                                text = "Suhu",
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                text = "16%",
                            )


                        }


                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(12.dp),
                        modifier = Modifier.width(114.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.iconair),
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Medium,
                                text = "Air Level",
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                text = "16%",
                            )


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
                                    text = "EC 0.0",
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
                                painter = painterResource(id = R.drawable.iconlampu),
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Medium,
                                text = "Lampu",
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = poppinsFamily,
                                fontWeight = FontWeight.Bold,
                                text = "On",
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
                                text = "Status",
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
                                    text = "Hari ke -10",
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
                                    text = "H-2 Percabangan",
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
fun HomeScreenPreview() {
    HomeScreen()
}
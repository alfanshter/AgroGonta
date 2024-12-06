package com.ptpws.agrogontafarm.ui.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ptpws.agrogontafarm.R
import com.ptpws.agrogontafarm.ui.auth.AuthActivity
import com.ptpws.agrogontafarm.ui.auth.AuthViewModel
import com.ptpws.agrogontafarm.ui.common.poppinsFamily
import com.ptpws.agrogontafarm.ui.utils.startNewActivity

@Composable
fun ProfilScreen(authViewModel: AuthViewModel) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.bgprofil), contentDescription = null)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 100.dp)
        ) {

            Text(
                text = "Hey, Alfan \uD83C\uDF3F",
                fontSize = 32.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(21.dp))

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
                                    text = "Bahasa",
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
                                    text = "Indonesia",
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

                    //logout
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(Modifier.clickable {
                        authViewModel.logout()
                        context.startNewActivity(AuthActivity::class.java)
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
                                    text = "Logout",
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

@Preview
@Composable
fun ProfilScreenPreview() {

    ProfilScreen(hiltViewModel())

}
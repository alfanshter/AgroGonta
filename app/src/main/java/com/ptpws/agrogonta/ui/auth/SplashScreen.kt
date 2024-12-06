package com.ptpws.agrogontafarm.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.ptpws.agrogontafarm.R
import com.ptpws.agrogontafarm.ui.AppScreen
import com.ptpws.agrogontafarm.ui.common.poppinsFamily

@Composable
fun SplashScreen(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bgcolor),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logoagro),
                contentDescription = null,
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(65.dp))

            Text(
                text = "Petani Moderen dengan teknologi IoT\n" +
                        "menuju indonesia EMAS",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = { navController.navigate(AppScreen.Auth.Login.route) },
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 80.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(4.dp)
                    ), colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(
                        id = R.color.hijau
                    )
                )
            }


        }

    }
}

@Preview
@Composable
fun SplashScreenView() {
    SplashScreen(navController = rememberNavController())
}
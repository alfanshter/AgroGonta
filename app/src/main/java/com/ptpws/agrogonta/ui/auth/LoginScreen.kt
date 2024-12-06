package com.ptpws.agrogontafarm.ui.auth

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.ui.common.poppinsFamily
import com.ptpws.agrogontafarm.ui.faker.FakeViewModelProvider
import com.ptpws.agrogontafarm.ui.home.HomeActivity
import com.ptpws.agrogontafarm.ui.utils.startNewActivity
import com.ptpws.agrogontafarm.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(navController: NavController,viewModel: AuthViewModel) {

    var email by remember {
        mutableStateOf(String())
    }
    var password by remember {
        mutableStateOf(String())
    }

    val loginFlow = viewModel.loginFlow.collectAsState()

    val context = LocalContext.current



    Box(modifier = Modifier.fillMaxSize()) {


        loginFlow.value?.let {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                            context.startNewActivity(HomeActivity::class.java)
                    }
                }
                Resource.Loading -> {
                    CircularProgressIndicator()
                }

                else -> {}
            }
        }
        Image(
            painter = painterResource(id = R.drawable.bgcolor),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Halo, Petani Moderen",
                color = Color.White,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 30.sp,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Jadilah petani sukses bersama agrogontafarm. Solusi terbaik untuk pertanian anda!",
                color = Color.White,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(48.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Email", color = Color.LightGray) },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_mail_outline_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color.LightGray)
                    )

                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White, // Untuk menghapus garis bawah saat fokus
                    unfocusedIndicatorColor = Color.White, // Untuk menghapus garis bawah saat tidak fokus
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,

                    ),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.white)
                ),
            )

            Spacer(modifier = Modifier.height(35.dp))

            var passwordVisible by rememberSaveable {
                mutableStateOf(false)
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Password", color = Color.LightGray) },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_lock_outline_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color.LightGray)
                    )

                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White, // Untuk menghapus garis bawah saat fokus
                    unfocusedIndicatorColor = Color.White, // Untuk menghapus garis bawah saat tidak fokus
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,

                    ),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.white)
                ),

                trailingIcon = {
                    val image = if (passwordVisible)
                        painterResource(id = R.drawable.baseline_visibility_24) else
                        painterResource(id = R.drawable.baseline_visibility_off_24)

                    //please
                    val description = if (passwordVisible) "Hide Password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = image, contentDescription = description, tint = Color.LightGray)

                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Lupa Password",
                color = Color.White,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.login(email.trim(), password.trim())
                },
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 90.dp)
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
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController(), FakeViewModelProvider.provideAuthViewModel())
}
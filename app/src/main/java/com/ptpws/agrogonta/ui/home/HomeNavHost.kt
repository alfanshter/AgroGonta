package com.ptpws.agrogontafarm.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ptpws.agrogonta.ui.log.flushing.FlushingScreen
import com.ptpws.agrogonta.ui.log.penyiraman.PenyiramanScreen
import com.ptpws.agrogontafarm.R
import com.ptpws.agrogontafarm.ui.AppScreen
import com.ptpws.agrogontafarm.ui.log.LogScreen
import com.ptpws.agrogontafarm.ui.profil.ProfilScreen
import com.ptpws.agrogontafarm.ui.theme.agrogontafarmTheme


@Composable
fun HomeNavHost(rememberNavController: NavHostController, initialRoute: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MyBottomAppBar()
    }
}

@Composable
fun MyBottomAppBar() {
    val navigationController = rememberNavController()

    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }


    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                modifier = Modifier.shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
            ) {
                IconButton(onClick = {
                    selected.value = Icons.Default.Home
                    navigationController.navigate(AppScreen.Home.Dashboard.route) {
                        popUpTo(0)
                    }
                }, modifier = Modifier.weight(1f)) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconhome),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.Home) colorResource(id = R.color.hijau) else Color.LightGray

                    )
                }

                IconButton(onClick = {
                    selected.value = Icons.Default.AddCard
                    navigationController.navigate(AppScreen.Home.Log.route) {
                        popUpTo(0)
                    }
                }, modifier = Modifier.weight(1f)) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconlog),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.AddCard) colorResource(id = R.color.hijau) else colorResource(
                            id = R.color.black
                        )
                    )
                }


                IconButton(onClick = {
                    selected.value = Icons.Default.Notifications
                    navigationController.navigate(AppScreen.Home.Profil.route) {
                        popUpTo(0)
                    }
                }, modifier = Modifier.weight(1f)) {
                    Icon(
                        painter = painterResource(id = R.drawable.iconprofil),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected.value == Icons.Default.Notifications) colorResource(id = R.color.hijau) else colorResource(
                            id = R.color.black
                        )
                    )
                }

            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = AppScreen.Home.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(AppScreen.Home.Dashboard.route) { HomeScreen(homeViewModel = hiltViewModel(),navigationController) }
            composable(AppScreen.Home.Log.route) { LogScreen(ghViewModel = hiltViewModel(), navigationController) }
            composable(AppScreen.Penyiraman.route) { PenyiramanScreen(penyiramanViewModel = hiltViewModel()) }
            composable(AppScreen.Flushing.route) { FlushingScreen(penyiramanViewModel = hiltViewModel()) }
            composable(AppScreen.Home.Profil.route) { ProfilScreen(authViewModel = hiltViewModel()) }
//            profilNav(navigationController )

        }

    }
}

@Preview
@Composable
fun MyBottomAppBarrPreview() {
    agrogontafarmTheme {
        MyBottomAppBar()
    }
}
package com.ptpws.agrogontafarm.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.ptpws.agrogontafarm.ui.AppScreen
import com.ptpws.agrogontafarm.ui.home.ui.theme.agrogontafarmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val initialRoute = intent.getStringExtra("route") ?: AppScreen.Home.Dashboard.route

        Log.d("dinda", "onCreate: $initialRoute ")
        setContent {
            agrogontafarmTheme {
                HomeNavHost(rememberNavController(), initialRoute)
            }
        }
    }

}
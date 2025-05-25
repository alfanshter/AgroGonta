package com.ptpws.agrogontafarm.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ptpws.agrogontafarm.R


sealed class AppScreen(@StringRes val title: Int, @DrawableRes val icon: Int, val route: String) {

    object Auth : AppScreen(R.string.app_name, R.drawable.baseline_home_24, "nav_auth") {
        object Splash : AppScreen(R.string.splashscreen, R.drawable.baseline_home_24, "splash")
        object Login : AppScreen(R.string.login, R.drawable.baseline_home_24, "login")
    }

    object Home : AppScreen(R.string.app_name, R.drawable.baseline_home_24, "nav_home") {
        object Dashboard : AppScreen(R.string.dashboard, R.drawable.baseline_home_24, "dashboard")
        object Log : AppScreen(R.string.log, R.drawable.baseline_home_24, "log")
        object Profil : AppScreen(R.string.profil, R.drawable.baseline_home_24, "profil")
    }

    object Penyiraman : AppScreen(R.string.penyiraman, R.drawable.iconlog, "penyiraman")
    object Flushing : AppScreen(R.string.flushing, R.drawable.iconlog, "flushing")


    object Control : AppScreen(R.string.control, R.drawable.iconlog, "control")

    object Profil : AppScreen(R.string.profil, R.drawable.iconprofil, "nav_profil") {
        object Home : AppScreen(R.string.profil, R.drawable.iconprofil, "profil")
    }

    object Logout : AppScreen(R.string.logout, R.drawable.baseline_home_24, "logout")


}

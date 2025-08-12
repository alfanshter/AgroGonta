plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.ptpws.agrogontafarm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ptpws.agrogontafarm"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "META-INF/io.netty.versions.properties"
            excludes += "META-INF/INDEX.LIST"        }
    }
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.places)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.storage)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.firebase.database)
    implementation(libs.androidx.animation.android)
    implementation(libs.androidx.animation.android)
    implementation(libs.androidx.animation.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)

    // Room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    //Splash Screen
    implementation(libs.androidx.core.splashscreen)

    //Cloudy for blurring effect
    implementation(libs.cloudy)

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // KotlinX Serialization
    implementation(libs.kotlinx.serialization.json)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)


    //Dagger-Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Datastore
    implementation (libs.androidx.datastore.preferences)

    //Compose Foundation
    implementation (libs.androidx.foundation)

    //Accompanist
    implementation (libs.accompanist.systemuicontroller)
    implementation (libs.accompanist.permissions) // Ganti dengan versi terbaru
    implementation (libs.accompanist.placeholder.material)
    implementation (libs.accompanist.swiperefresh)

    //firebase

    implementation(libs.play.services.auth)
    implementation (libs.firebase.auth)
    implementation (libs.play.services.auth.v2120)
//    //Room
//    val roomVersion = "2.6.1"
//    ksp("androidx.room:room-compiler:$roomVersion")
//    implementation ("androidx.room:room-runtime:$roomVersion")
//    implementation ("androidx.room:room-ktx:$roomVersion")

    implementation (libs.androidx.constraintlayout.compose)
    implementation (libs.androidx.constraintlayout)

    implementation(libs.androidx.material.icons.extended)

    implementation (libs.retrofit2.kotlin.coroutines.adapter)

    //security sharepreference
    implementation (libs.androidx.security.crypto)

    //maps
    implementation(libs.maps.compose)
    //lokasi
    implementation (libs.play.services.location)

    //videoplayer
    implementation(libs.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)

    implementation(libs.core)

    implementation("com.hivemq:hivemq-mqtt-client:1.3.7")


    implementation (libs.androidx.datastore.preferences.v111)
    implementation (libs.itext7.core)
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.ktor.client.cio)
    implementation (libs.ktor.client.json)
    implementation (libs.ktor.client.serialization)
    implementation (libs.kotlinx.coroutines.core.v181)
    implementation (libs.kotlinx.coroutines.android.v181)
    implementation (libs.ui)
    implementation (libs.material3)
    implementation (libs.ui.tooling)
    implementation (libs.androidx.constraintlayout)


}


package com.ptpws.agrogontafarm.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ptpws.agrogonta.api.ModeApi
import com.ptpws.agrogonta.api.WeatherApi
import com.ptpws.agrogonta.data.repo.ModeRepository
import com.ptpws.agrogonta.data.repo.ModeRepositoryImpl
import com.ptpws.agrogonta.data.repo.WeatherRepository
import com.ptpws.agrogonta.data.repo.WeatherRepositoryImpl
import com.ptpws.agrogonta.utils.Constant
import com.ptpws.agrogontafarm.data.auth.AuthRepository
import com.ptpws.agrogontafarm.data.auth.AuthRepositoryImp
import com.ptpws.agrogontafarm.data.penyiraman.PenyiramanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("weatherRetrofit") // kasih nama supaya bisa dibedakan
    fun provideWeatherRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.WEATHER_API_BASE_URL)  // base URL weather API
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    @Named("modeRetrofit")
    fun provideModeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.MODE_API_BASE_URL)  // base URL mode API
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(@Named("weatherRetrofit") retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideModeApi(@Named("modeRetrofit") retrofit: Retrofit): ModeApi {
        return retrofit.create(ModeApi::class.java)
    }

    // PROVIDE REPOSITORIES seperti biasa
    @Provides
    fun provideWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository = impl

    @Provides
    fun provideModeRepository(impl: ModeRepositoryImpl): ModeRepository = impl

    // Contoh FirebaseAuth, AuthRepository, dan PenyiramanRepository tetap sama
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImp): AuthRepository = impl

    @Singleton
    @Provides
    fun providePenyiramanRepository(
        @ApplicationContext context: Context
    ): PenyiramanRepository {
        return PenyiramanRepository(context)
    }
}

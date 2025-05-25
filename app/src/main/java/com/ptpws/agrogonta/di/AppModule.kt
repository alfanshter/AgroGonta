package com.ptpws.agrogontafarm.di

import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ptpws.agrogonta.api.WeatherApi
import com.ptpws.agrogonta.data.repo.WeatherRepository
import com.ptpws.agrogonta.data.repo.WeatherRepositoryImpl
import com.ptpws.agrogonta.utils.Constant
import com.ptpws.agrogontafarm.data.auth.AuthRepository
import com.ptpws.agrogontafarm.data.auth.AuthRepositoryImp
import com.ptpws.agrogontafarm.data.penyiraman.PenyiramanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constant.API)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImp): AuthRepository = impl

    @Provides
    @Singleton
    fun providePenyiramanRepository(): PenyiramanRepository {
        // Berikan implementasi repository asli atau mock di sini
        return PenyiramanRepository() // Firebase implementation
    }

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }


    @Provides
    fun provideWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository = impl

}
package com.ptpws.agrogontafarm.di

import com.google.firebase.auth.FirebaseAuth
import com.ptpws.agrogontafarm.data.auth.AuthRepository
import com.ptpws.agrogontafarm.data.auth.AuthRepositoryImp
import com.ptpws.agrogontafarm.data.penyiraman.PenyiramanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

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
}
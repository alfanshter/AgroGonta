package com.ptpws.agrogontafarm.data.auth

import com.google.firebase.auth.FirebaseUser
import com.ptpws.agrogontafarm.data.Resource

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    fun logout()


}
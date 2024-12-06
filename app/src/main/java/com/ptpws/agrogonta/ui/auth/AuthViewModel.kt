package com.ptpws.agrogontafarm.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.ptpws.agrogontafarm.data.Resource
import com.ptpws.agrogontafarm.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val authRepository: AuthRepository
) :ViewModel(){


   private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
   val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow


   init {
      if (authRepository.currentUser != null) {
         _loginFlow.value = Resource.Success(authRepository.currentUser!!)
      }
   }

   fun login(email: String, password: String) = viewModelScope.launch {
      _loginFlow.value = Resource.Loading
      val result = authRepository.login(email, password)
      _loginFlow.value = result
   }



   fun logout() {
      authRepository.logout()
      _loginFlow.value = null
   }
}
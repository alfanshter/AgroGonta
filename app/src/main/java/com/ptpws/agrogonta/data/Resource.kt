package com.ptpws.agrogontafarm.data

import java.lang.Exception

sealed class Resource<out R> {
    data class Success<out R>(val result: R): Resource<R>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    data class Error<T>(val message: String) : Resource<T>()
    object Loading: Resource<Nothing>()
    object Idle : Resource<Nothing>()
    class Loadings<T> : Resource<T>()

}
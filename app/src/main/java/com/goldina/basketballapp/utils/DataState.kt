package com.goldina.basketballapp.utils

sealed class DataState<out R> {
    data class Success<out T>(val data: T?): DataState<T>()
    data class Exception(val exception: Throwable): DataState<Nothing>()
    data class Error(val error: String): DataState<Nothing>()
    object Loading: DataState<Nothing>()
}

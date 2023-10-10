package com.jlndev.baseservice.state

sealed class ResponseState<out T> {
    data class Loading(val isLoading: Boolean) : ResponseState<Nothing>()
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Error(val throwable: Throwable) : ResponseState<Nothing>()
}
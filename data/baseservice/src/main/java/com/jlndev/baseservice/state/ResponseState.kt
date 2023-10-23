package com.jlndev.baseservice.state

import java.io.Serializable

sealed class ResponseState<out T> : Serializable {
    data class Loading(val isLoading: Boolean) : ResponseState<Nothing>()
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Error(val throwable: Throwable) : ResponseState<Nothing>()
}
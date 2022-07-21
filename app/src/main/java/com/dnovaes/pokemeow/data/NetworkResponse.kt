package com.dnovaes.pokemeow.data

sealed class NetworkResponse <out T> {
    class Success<T>(val data: T) : NetworkResponse<T>()
    class Error(val message: String) : NetworkResponse<Nothing>()
}

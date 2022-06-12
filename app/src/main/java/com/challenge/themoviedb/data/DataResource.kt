package com.challenge.themoviedb.data

sealed class DataResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : DataResource<T>(data)
    class Error<T>(message: String, data: T? = null) : DataResource<T>(data, message)
    class Loading<T>(data: T? = null) : DataResource<T>(data)
}

const val IOEXCEPTION_ERROR_MESSAGE = "Couldn't reach server. Check your internet connection."
const val HTTP_EXCEPTION_ERROR_MESSAGE = "An unexpected error occurred."
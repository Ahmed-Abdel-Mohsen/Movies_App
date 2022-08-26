package com.example.base.data

/**
 * Resource state management class for data source
 * @param T success state result type.
 */
sealed class Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val exceptionL: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
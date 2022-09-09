package net.trexis.shafikexcersie.commons

sealed class ApiResponse<out T> {
    object Loading : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
    data class Error(val exception: Throwable) : ApiResponse<Nothing>()
    data class Success<T>(val data: T) : ApiResponse<T>()
}
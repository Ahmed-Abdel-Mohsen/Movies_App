package com.example.base.data

import retrofit2.Response

open class BaseRemoteDataSource {

    @Throws(
        InternalServerErrorException::class,
        UnauthorizedApiException::class,
        ServerMessageApiException::class,
        ApiException::class,
        NoContentException::class,
        CustomSuccessException::class
    )
    suspend fun <T> makeRequest(call: suspend () -> Response<T>): T {
        return safeApiCall(call)
    }

    @Throws(
        InternalServerErrorException::class,
        UnauthorizedApiException::class,
        ServerMessageApiException::class,
        ApiException::class,
        NoContentException::class,
        CustomSuccessException::class
    )
    private suspend fun <T> safeApiCall(call: suspend () -> Response<T>): T {
        val result = call()
        return when (result.isSuccessful) {
            false -> {
                val responseCode = result.code()
                val errorMessage = result.message()
                when {
                    responseCode == 500 -> throw InternalServerErrorException()
                    responseCode == 401 -> throw UnauthorizedApiException()
                    errorMessage.isNotEmpty() -> throw ServerMessageApiException(
                        errorMessage,
                        responseCode
                    )
                    else -> throw ApiException(
                        responseCode
                    )
                }
            }
            true -> {
                when (result.code()) {
                    200, 201 -> result.body()!!
                    204 -> throw NoContentException()
                    else -> throw CustomSuccessException(code = result.code())
                }
            }
        }
    }
}
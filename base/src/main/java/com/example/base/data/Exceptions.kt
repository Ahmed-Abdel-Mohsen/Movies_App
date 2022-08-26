package com.example.base.data

class NoInternetException(message: String? = "Not connected to internet") :
    RuntimeException(message)

/**
 * General network API exception.
 */
open class ApiException(
    private val code: Int,
    message: String? = "API response is not successful (code: $code)"
) : RuntimeException(message)

/**
 * Network API exception thrown when response code is 401 (unauthorized).
 */
class UnauthorizedApiException(code: Int = 401, message: String? = "Unauthorized (code: $code)") :
    ApiException(code, message)

/**
 * Network API exception thrown when response is 500 (server error).
 */
class InternalServerErrorException(
    code: Int = 500,
    message: String? = "Internal server error (code: $code)"
) :
    ApiException(code, message)

class ServerMessageApiException(
    private val serverLocalizedMessage: String?,
    code: Int,
    exceptionMessage: String? = "(Code: $code) Localized backend error message: $serverLocalizedMessage"
) : ApiException(code, exceptionMessage)

/**
 * Network API exception thrown when response is 204 (no content).
 */
class NoContentException(
    code: Int = 204,
    message: String? = "No content (code: $code)",
) : ApiException(code, message)

/**
 * Network API exception thrown when response is success but neither 200, 201 nor 204 .
 */
class CustomSuccessException(
    code: Int,
    message: String? = "Success response exception (code: $code)"
) : ApiException(code, message)

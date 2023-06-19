package com.lloydheyrosa.commons.utils

/**
 * Class representing the results from an API call.
 *
 * @param T Expected type of the success value. This is `Unit` if blank.
 * @param X Expected type of the error payload from server failures. This is `Unit` if blank.
 */
sealed interface ApiResult<out T, out X>

/**
 * Represents a successful result.
 */
data class Success<out T>(val value: T) : ApiResult<T, Nothing> {

    companion object {
        private val UnitSuccess = Success(Unit)

        /**
         * Returns a [Success] containing a [Unit] value.
         */
        operator fun invoke(): Success<Unit> = UnitSuccess
    }
}

/**
 * Represents a failed operation.
 *
 * This can be further subdivided into:
 * - [ServerSideFailure]
 * - [ConnectivityFailure]
 * - [ParsingFailure]
 * - [GeneralFailure]
 */
sealed interface Failure<out X> : ApiResult<Nothing, X>

/**
 * Represents an unsuccessful server response.
 *
 * This typically happens on non-`HTTP 2xx` responses.
 */
data class ServerSideFailure<out X>(
    val statusCode: Int,
    val payload: X,
) : Failure<X> {

    fun <R> withPayload(newPayload: R): ServerSideFailure<R> {
        return ServerSideFailure(
            statusCode = statusCode,
            payload = newPayload,
        )
    }

    companion object {
        /**
         * Returns a [ServerSideFailure] containing a [Unit] payload.
         */
        operator fun invoke(statusCode: Int): ServerSideFailure<Unit> {
            return ServerSideFailure(statusCode, Unit)
        }
    }
}

/**
 * Represents failures not explicitly caused by the server-side.
 *
 * This can be further subdivided into:
 * - [AuthenticationFailure]
 * - [ConnectivityFailure]
 * - [ParsingFailure]
 * - [GeneralFailure]
 */
sealed interface MiscellaneousFailure : Failure<Nothing>

/**
 * Represents an authentication failure.
 *
 * This typically happens with expired tokens or invalid/missing credentials.
 */
object AuthenticationFailure : MiscellaneousFailure

/**
 * Represents a failure due to connection problems between client and server.
 *
 * This typically happens if there are internet connection issues, or if the server is down.
 */
object ConnectivityFailure : MiscellaneousFailure

/**
 * Represents problems with deserializing response values.
 *
 * This typically happens when the expected response data structure is different from the actual
 * structure.
 */
object ParsingFailure : MiscellaneousFailure

/**
 * Represents a generic failure not covered by the other `Failure` types.
 */
object GeneralFailure : MiscellaneousFailure


/**
 * Converts the success value based on [transform].
 */
inline fun <T, X, R> ApiResult<T, X>.convertValue(transform: (T) -> R): ApiResult<R, X> {
    return when (this) {
        is Success -> Success(transform(value))
        is Failure -> this
    }
}

/**
 * Converts the error payload based on [transform].
 */
inline fun <T, X, R> ApiResult<T, X>.convertErrorPayload(transform: (X) -> R): ApiResult<T, R> {
    return when (this) {
        is ServerSideFailure -> withPayload(transform(payload))
        is MiscellaneousFailure -> this
        is Success -> this
    }
}

/**
 * Converts `HTTP 404` errors to [Success] with the value from [defaultSupplier].
 */
inline fun <T, X> ApiResult<T, X>.defaultIfNotFound(defaultSupplier: () -> T): ApiResult<T, X> {
    return if (this is ServerSideFailure && statusCode == 404) {
        Success(defaultSupplier())
    } else {
        this
    }
}

package com.lloydheyrosa.contactlist.data.utils

import android.util.Log
import com.fasterxml.jackson.core.JsonProcessingException
import com.lloydheyrosa.commons.utils.ApiResult
import com.lloydheyrosa.commons.utils.AuthenticationFailure
import com.lloydheyrosa.commons.utils.ConnectivityFailure
import com.lloydheyrosa.commons.utils.GeneralFailure
import com.lloydheyrosa.commons.utils.ParsingFailure
import com.lloydheyrosa.commons.utils.ServerSideFailure
import com.lloydheyrosa.commons.utils.Success
import retrofit2.HttpException
import java.io.IOException

inline fun <T, X> wrapResult(
    logTag: String,
    forbiddenIsAuthFailure: Boolean = true,
    block: () -> ApiResult<T, X>,
    errorPayload: (errorBody: String?) -> X,
): ApiResult<T, X> {
    return try {
        block()
    } catch (e: HttpException) {
        Log.d(logTag, "Server returned a failure response", e)

        val statusCode = e.code()

        if (statusCode == 403 && forbiddenIsAuthFailure) {
            AuthenticationFailure
        } else {
            val errorBody = e.response()?.errorBody()?.string()
            ServerSideFailure(statusCode, errorPayload(errorBody))
        }
    } catch (e: JsonProcessingException) {
        Log.d(logTag, "Encountered an error while parsing JSON", e)
        ParsingFailure
    } catch (e: IOException) {
        Log.d(logTag, "Failed to connect to server", e)
        ConnectivityFailure
    } catch (e: Exception) {
        Log.d(logTag, "Encountered an error", e)
        GeneralFailure
    }
}

inline fun <T> wrapResult(
    logTag: String,
    forbiddenIsAuthFailure: Boolean = true,
    block: () -> ApiResult<T, Unit>,
): ApiResult<T, Unit> {
    return wrapResult(
        logTag = logTag,
        forbiddenIsAuthFailure = forbiddenIsAuthFailure,
        block = block,
        errorPayload = {},
    )
}

inline fun <T, X> wrapResultValue(
    logTag: String,
    forbiddenIsAuthFailure: Boolean = true,
    block: () -> T,
    errorPayload: (errorBody: String?) -> X,
): ApiResult<T, X> {
    return wrapResult(
        logTag = logTag,
        forbiddenIsAuthFailure = forbiddenIsAuthFailure,
        block = { Success(block()) },
        errorPayload = errorPayload,
    )
}

inline fun <T> wrapResultValue(
    logTag: String,
    forbiddenIsAuthFailure: Boolean = true,
    block: () -> T,
): ApiResult<T, Unit> {
    return wrapResultValue(
        logTag = logTag,
        forbiddenIsAuthFailure = forbiddenIsAuthFailure,
        block = block,
        errorPayload = {},
    )
}
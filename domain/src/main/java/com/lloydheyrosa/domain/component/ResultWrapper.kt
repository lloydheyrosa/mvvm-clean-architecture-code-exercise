package com.lloydheyrosa.domain.component

import com.lloydheyrosa.commons.utils.ApiResult
import com.lloydheyrosa.commons.utils.Success


interface ResultWrapper {

    suspend fun <T, X> wrapResult(
        logTag: String,
        forbiddenIsAuthFailure: Boolean = true,
        block: suspend () -> ApiResult<T, X>,
        errorPayload: suspend (errorBody: String?) -> X,
    ): ApiResult<T, X>
}

suspend fun <T> ResultWrapper.wrapResult(
    logTag: String,
    forbiddenIsAuthFailure: Boolean = true,
    block: suspend () -> ApiResult<T, Unit>,
): ApiResult<T, Unit> {
    return wrapResult(
        logTag = logTag,
        forbiddenIsAuthFailure = forbiddenIsAuthFailure,
        block = block,
        errorPayload = {},
    )
}

suspend fun <T, X> ResultWrapper.wrapResultValue(
    logTag: String,
    forbiddenIsAuthFailure: Boolean = true,
    block: suspend () -> T,
    errorPayload: suspend (errorBody: String?) -> X,
): ApiResult<T, X> {
    return wrapResult(
        logTag = logTag,
        forbiddenIsAuthFailure = forbiddenIsAuthFailure,
        block = { Success(block()) },
        errorPayload = errorPayload,
    )
}

suspend fun <T> ResultWrapper.wrapResultValue(
    logTag: String,
    forbiddenIsAuthFailure: Boolean = true,
    block: suspend () -> T,
): ApiResult<T, Unit> {
    return wrapResultValue(
        logTag = logTag,
        forbiddenIsAuthFailure = forbiddenIsAuthFailure,
        block = block,
        errorPayload = {},
    )
}
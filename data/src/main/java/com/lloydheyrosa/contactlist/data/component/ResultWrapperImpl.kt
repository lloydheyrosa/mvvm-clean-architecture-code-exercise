package com.lloydheyrosa.contactlist.data.component

import com.lloydheyrosa.commons.utils.ApiResult
import com.lloydheyrosa.domain.component.ResultWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultWrapperImpl @Inject constructor() : ResultWrapper {

    override suspend fun <T, X> wrapResult(
        logTag: String,
        forbiddenIsAuthFailure: Boolean,
        block: suspend () -> ApiResult<T, X>,
        errorPayload: suspend (errorBody: String?) -> X,
    ): ApiResult<T, X> {
        return com.lloydheyrosa.contactlist.data.utils.wrapResult(
            logTag = logTag,
            forbiddenIsAuthFailure = forbiddenIsAuthFailure,
            block = { block() },
            errorPayload = { errorPayload(it) },
        )
    }
}

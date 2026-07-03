package com.crewcloud.apps.crewchat.data.network

import com.crewcloud.apps.crewchat.domain.model.Result
import com.crewcloud.apps.crewchat.domain.model.mapThrowableToAppError
import kotlinx.coroutines.CancellationException

/**
 * Created by BM Anderson on 3/7/26.
 */

suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
): Result<T> {
    return try {
        Result.ResultSuccess(result = apiCall())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.Failure(appError = mapThrowableToAppError(e))
    }
}
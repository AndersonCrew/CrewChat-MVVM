package com.crewcloud.apps.crewchat.domain.model

import retrofit2.HttpException
import java.io.IOException

/**
 * Created by BM Anderson on 3/7/26.
 */

sealed class Result <out T>{

    data class ResultSuccess<T>(
        val result: T
    ): Result<T>()

    data class Failure(
        val appError: AppError
    ): Result<Nothing>()
}

data class AppError(
    val error: String,
    val type: AppErrorType = AppErrorType.UNKNOWN
)

fun mapThrowableToAppError(e: Throwable): AppError {
    return when (e) {
        is HttpException -> when (e.code()) {
            401 -> AppError("401 Unauthorized", AppErrorType.UNAUTHORISED)
            404 -> AppError("404 Not Found", AppErrorType.NOT_FOUND)
            in 500..599 -> AppError("Internal Server Error", AppErrorType.SERVER)
            else -> AppError("HTTP ${e.code()}", AppErrorType.UNKNOWN)
        }

        is IOException -> AppError("Internet connection error", AppErrorType.NETWORK)
        else -> AppError(e.localizedMessage ?: "Unknown Error",AppErrorType.UNKNOWN)
    }
}

enum class AppErrorType {
    UNAUTHORISED,
    NOT_FOUND,
    TIMEOUT,
    NETWORK,
    SERVER,
    UNKNOWN
}
package com.crewcloud.apps.crewchat.data.dto.base

/**
 * Created by BM Anderson on 3/7/26.
 */
data class BaseResponse<out T>(
    val d: Data<T>
)

data class Data<out T>(
    val data: T,
    val success: Int,
    val error: BaseError?= null
)

data class BaseError(
    val code: Int,
    val message: String
)

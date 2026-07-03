package com.crewcloud.apps.crewchat.data.network.api

import com.crewcloud.apps.crewchat.data.dto.base.BaseResponse
import com.crewcloud.apps.crewchat.data.dto.check_api.CheckApiDTO
import com.crewcloud.apps.crewchat.data.dto.check_api.CheckApiRequest
import com.crewcloud.apps.crewchat.data.dto.check_ssl.CheckSSLDTO
import com.crewcloud.apps.crewchat.data.dto.check_ssl.CheckSSLRequest
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by BM Anderson on 2/7/26.
 */
interface StaticApiService {
    @POST("SSL_Check")
    suspend fun checkSSL(
        @Body request: CheckSSLRequest,
    ): BaseResponse<CheckSSLDTO>

    @POST("API_Check")
    suspend fun checkApi(
        @Body request: CheckApiRequest,
    ): BaseResponse<CheckApiDTO>
}
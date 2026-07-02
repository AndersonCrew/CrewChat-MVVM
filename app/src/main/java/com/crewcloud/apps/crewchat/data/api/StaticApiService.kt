package com.crewcloud.apps.crewchat.data.api

import retrofit2.http.POST

/**
 * Created by BM Anderson on 2/7/26.
 */
interface StaticApiService {
    @POST("/API_Check")
    suspend fun checkApi()
}
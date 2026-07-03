package com.crewcloud.apps.crewchat.data.network.api

import com.crewcloud.apps.crewchat.data.dto.base.BaseResponse
import com.crewcloud.apps.crewchat.data.dto.login.LoginRequest
import com.crewcloud.apps.crewchat.data.dto.login.UserDTO
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by BM Anderson on 2/7/26.
 */
interface DazoneApiService {
    @POST("/UI/WebService/WebServiceCenter.asmx/Login_v5")
    suspend fun loginV5(
        @Body request: LoginRequest
    ): BaseResponse<UserDTO>

    @POST("/UI/WebService/WebServiceCenter.asmx/Login_CrewChat")
    suspend fun loginCrewChat(
        @Body request: LoginRequest
    ): BaseResponse<UserDTO>
}
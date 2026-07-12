package com.crewcloud.apps.crewchat.data.network.api

import com.crewcloud.apps.crewchat.data.dto.base.BaseResponse
import com.crewcloud.apps.crewchat.data.dto.base.EmptyDTO
import com.crewcloud.apps.crewchat.data.dto.chatting.ChattingDto
import com.crewcloud.apps.crewchat.data.dto.chatting.ChattingRequest
import com.crewcloud.apps.crewchat.data.dto.check_device_access.CheckDeviceAccessRequest
import com.crewcloud.apps.crewchat.data.dto.current_chat.CurrentChatDto
import com.crewcloud.apps.crewchat.data.dto.current_chat.CurrentChatRequest
import com.crewcloud.apps.crewchat.data.dto.insert_fcm.UpdateNotificationRequest
import com.crewcloud.apps.crewchat.data.dto.insert_fcm.UpdateNotificationResponse
import com.crewcloud.apps.crewchat.data.dto.login.LoginRequest
import com.crewcloud.apps.crewchat.data.dto.login.UserDTO
import com.crewcloud.apps.crewchat.data.dto.organization.DepartmentDto
import com.crewcloud.apps.crewchat.data.dto.organization.DepartmentRequest
import com.crewcloud.apps.crewchat.data.dto.organization.EmployeeDto
import com.crewcloud.apps.crewchat.data.dto.organization.EmployeeRequest
import com.crewcloud.apps.crewchat.domain.model.CurrentChat
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

    @POST("/UI/WebService/WebServiceCenter.asmx/CheckMobileDevicesAccessrestrictions")
    suspend fun checkDevicesAccess(
        @Body request: CheckDeviceAccessRequest
    ): BaseResponse<EmptyDTO>

    @POST("/UI/CrewChat/MobileWebService.asmx/RequestData")
    suspend fun insertAndroidDevice(
        @Body request: UpdateNotificationRequest
    ): UpdateNotificationResponse

    @POST("/UI/WebService/WebServiceCenter.asmx/GetDepartments")
    suspend fun getAllDepartments(
        @Body request: DepartmentRequest
    ): BaseResponse<List<DepartmentDto>>

    @POST("/UI/WebService/WebServiceCenter.asmx/GetUsersByDepartment")
    suspend fun getUsersFromDepartmentNo(
        @Body request : EmployeeRequest
    ): BaseResponse<List<EmployeeDto>>

    @POST("/UI/CrewChat/MobileWebService.asmx/RequestData")
    suspend fun getCurrentChatList(
        @Body request : CurrentChatRequest
    ): BaseResponse<List<CurrentChatDto>>

    @POST("/UI/CrewChat/MobileWebService.asmx/RequestData")
    suspend fun getChatList(
        @Body request : ChattingRequest
    ): BaseResponse<List<ChattingDto>>
}
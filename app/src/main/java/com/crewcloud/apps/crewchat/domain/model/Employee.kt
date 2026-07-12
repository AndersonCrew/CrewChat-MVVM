package com.crewcloud.apps.crewchat.domain.model

/**
 * Created by BM Anderson on 6/7/26.
 */
data class Employee(
    val id: Int,
    val username: String,
    val displayName: String,
    val email: String,
    val phoneNumber: String,
    val companyNumber: String,
    val extNumber: String,
    val gender: Int,
    val isUserPhotoActive: Boolean,
    val photoName: String,
    val isActive: Boolean,
    val departmentId: Int,
    val departmentName: String,
    val position: String,
    val duty: String,
    val fullComboName: String,
    val avatar: String
)

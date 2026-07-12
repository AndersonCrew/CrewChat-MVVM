package com.crewcloud.apps.crewchat.domain.repository

import com.crewcloud.apps.crewchat.data.local.entity.DepartmentEntity
import com.crewcloud.apps.crewchat.domain.model.Department
import com.crewcloud.apps.crewchat.domain.model.Employee
import com.crewcloud.apps.crewchat.domain.model.Result
import kotlinx.coroutines.flow.Flow

/**
 * Created by BM Anderson on 6/7/26.
 */
interface DepartmentRepository {
    fun observerDepartments(): Flow<List<Department>>
    fun observerEmployees(): Flow<List<Employee>>
    suspend fun getAllDepartment(): Result<List<Department>>
    suspend fun getAllEmployeesFromDepartments()
    suspend fun updateExpanded(departNo: Int, isExpanded: Boolean)

    suspend fun hasDepartment(): Boolean
    suspend fun hasEmployees(): Boolean
}
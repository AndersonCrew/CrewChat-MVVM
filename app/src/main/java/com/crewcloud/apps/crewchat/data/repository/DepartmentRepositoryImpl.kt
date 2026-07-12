package com.crewcloud.apps.crewchat.data.repository

import android.util.Log
import com.crewcloud.apps.crewchat.data.dto.organization.DepartmentRequest
import com.crewcloud.apps.crewchat.data.dto.organization.EmployeeRequest
import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStore
import com.crewcloud.apps.crewchat.data.local.SecureLocalStorage
import com.crewcloud.apps.crewchat.data.local.dao.DepartmentDao
import com.crewcloud.apps.crewchat.data.local.dao.EmployeeDao
import com.crewcloud.apps.crewchat.data.local.entity.DepartmentEntity
import com.crewcloud.apps.crewchat.data.local.entity.EmployeeEntity
import com.crewcloud.apps.crewchat.data.mapper.flattenToDepartmentEntities
import com.crewcloud.apps.crewchat.data.mapper.toDomain
import com.crewcloud.apps.crewchat.data.mapper.toEntity
import com.crewcloud.apps.crewchat.data.network.api.DazoneApiService
import com.crewcloud.apps.crewchat.data.network.safeApiCall
import com.crewcloud.apps.crewchat.domain.model.Department
import com.crewcloud.apps.crewchat.domain.model.Employee
import com.crewcloud.apps.crewchat.domain.model.Result
import kotlinx.coroutines.flow.flowOn
import com.crewcloud.apps.crewchat.domain.repository.DepartmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.chunked
import kotlin.collections.map

/**
 * Created by BM Anderson on 6/7/26.
 */
class DepartmentRepositoryImpl @Inject constructor(
    private val dazoneApiService: DazoneApiService,
    private val secureLocalStorage: SecureLocalStorage,
    private val departmentDao: DepartmentDao,
    private val employeeDao: EmployeeDao,
    private val appPreferenceDataStore: AppPreferenceDataStore
) : DepartmentRepository {
    override fun observerDepartments(): Flow<List<Department>> {
        return departmentDao.observeDepartments().map {
            it.map { departmentEntity ->
                departmentEntity.toDomain()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun observerEmployees(): Flow<List<Employee>> {
        return employeeDao.observeAllEmployees().map {
            it.map { employeeEntity ->
                employeeEntity.toDomain()
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllDepartment(): Result<List<Department>> {
        val request = DepartmentRequest(
            sessionId = secureLocalStorage.getSessionId() ?: ""
        )

        val result = safeApiCall { dazoneApiService.getAllDepartments(request) }
        return when (result) {
            is Result.Failure -> Result.Failure(appError = result.appError)
            is Result.ResultSuccess -> {
                val departments = result.result.d.data.map { it.toDomain() }
                val departmentEntities = departments.flattenToDepartmentEntities()
                departmentDao.replaceDepartments(departmentEntities)

                val allResults = mutableListOf<Result<List<EmployeeEntity>>>()
                departmentEntities.chunked(5).forEach { batch ->
                    val batchResults = coroutineScope {
                        batch.map { department ->
                            async {
                                getUsersFromDepartmentNo(department.departNo)
                            }
                        }.awaitAll()
                    }

                    allResults.addAll(batchResults)
                    val employees = allResults
                        .filterIsInstance<Result.ResultSuccess<List<EmployeeEntity>>>()
                        .flatMap { it.result }
                    employeeDao.replaceEmployees(employees)
                }

                Log.d(
                    "DepartmentRepository",
                    "getAllUsers Success with allResults size = ${allResults.size}"
                )
                Result.ResultSuccess(result = departments)
            }
        }
    }
    private suspend fun getUsersFromDepartmentNo(departmentNo: Int): Result<List<EmployeeEntity>> {
        Log.d("DepartmentRepository", "getUsersFromDepartmentNo departmentNo = $departmentNo")
        val request = EmployeeRequest(
            departNo = departmentNo,
            sessionId = secureLocalStorage.getSessionId() ?: ""
        )

        return when (val result =
            safeApiCall { dazoneApiService.getUsersFromDepartmentNo(request) }) {
            is Result.Failure -> result
            is Result.ResultSuccess -> Result.ResultSuccess(result = result.result.d.data.map { it.toEntity(url = appPreferenceDataStore.getBaseUrl()) })
        }
    }

    override suspend fun getAllEmployeesFromDepartments() {
        val allResults = mutableListOf<Result<List<EmployeeEntity>>>()
        val departments: List<DepartmentEntity> = departmentDao.getDepartments()
        departments.chunked(5).forEach { batch ->
            val batchResults = coroutineScope {
                batch.map { department ->
                    async {
                        getUsersFromDepartmentNo(department.departNo)
                    }
                }.awaitAll()
            }

            allResults.addAll(batchResults)
            val employees = allResults
                .filterIsInstance<Result.ResultSuccess<List<EmployeeEntity>>>()
                .flatMap { it.result }
            employeeDao.replaceEmployees(employees)
        }
    }

    override suspend fun updateExpanded(departNo: Int, isExpanded: Boolean) {
        departmentDao.updateExpanded(departNo, isExpanded)
    }

    override suspend fun hasDepartment(): Boolean {
        return departmentDao.hasDepartment()
    }

    override suspend fun hasEmployees(): Boolean {
        return employeeDao.hasEmployees()
    }
}
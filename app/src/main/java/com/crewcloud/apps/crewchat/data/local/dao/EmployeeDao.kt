package com.crewcloud.apps.crewchat.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.crewcloud.apps.crewchat.data.local.entity.EmployeeEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by BM Anderson on 7/7/26.
 */
@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    fun observeAllEmployees(): Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM employees WHERE departNo=:departNo")
    fun observeEmployeesByDepartNo(departNo: Int): Flow<List<EmployeeEntity>>

    @Query("DELETE FROM employees")
    suspend fun clearEmployees()

    @Upsert
    fun upsertEmployees(employees: List<EmployeeEntity>)

    @Query("SELECT EXISTS(SELECT 1 FROM employees LIMIT 1)")
    suspend fun hasEmployees(): Boolean

    @Transaction
    suspend fun replaceEmployees(employees: List<EmployeeEntity>) {
        clearEmployees()
        upsertEmployees(employees)
    }
}
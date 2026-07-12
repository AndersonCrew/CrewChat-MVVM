package com.crewcloud.apps.crewchat.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.crewcloud.apps.crewchat.data.local.entity.DepartmentEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by BM Anderson on 7/7/26.
 */
@Dao
interface DepartmentDao {
    @Upsert
    suspend fun upsertDepartments(departments: List<DepartmentEntity>)

    @Query("SELECT * FROM departments ORDER BY sortNo ASC, name ASC")
    fun observeDepartments(): Flow<List<DepartmentEntity>>

    @Query("SELECT * FROM departments ORDER BY sortNo ASC, name ASC")
    fun getDepartments(): List<DepartmentEntity>

    @Query("UPDATE departments SET isExpanded = :isExpanded WHERE departNo = :departNo")
    suspend fun updateExpanded(departNo: Int, isExpanded: Boolean)

    @Query("DELETE FROM departments")
    suspend fun clearDepartments()

    @Query("SELECT EXISTS(SELECT 1 FROM departments LIMIT 1)")
    suspend fun hasDepartment(): Boolean

    @Transaction
    suspend fun replaceDepartments(departments: List<DepartmentEntity>) {
        clearDepartments()
        upsertDepartments(departments)
    }
}

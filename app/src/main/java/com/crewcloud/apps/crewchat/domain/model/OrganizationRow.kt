package com.crewcloud.apps.crewchat.domain.model

/**
 * Created by BM Anderson on 7/7/26.
 */
sealed interface OrganizationRow {
    data class DepartmentRow(
        val department: Department,
        val level: Int
    ) : OrganizationRow

    data class EmployeeRow(
        val employee: Employee,
        val level: Int
    ) : OrganizationRow
}
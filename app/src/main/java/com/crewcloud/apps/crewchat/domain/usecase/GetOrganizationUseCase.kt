package com.crewcloud.apps.crewchat.domain.usecase

import com.crewcloud.apps.crewchat.domain.model.Department
import com.crewcloud.apps.crewchat.domain.model.Employee
import com.crewcloud.apps.crewchat.domain.model.OrganizationRow
import com.crewcloud.apps.crewchat.domain.repository.DepartmentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by BM Anderson on 6/7/26.
 */
class GetOrganizationUseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository,
) {
    operator fun invoke(): Flow<List<Department>> {
        return combine(
            departmentRepository.observerDepartments(),
            departmentRepository.observerEmployees()
        ) { departments, employees ->
            buildDepartmentTree(departments, employees)
        }.flowOn(Dispatchers.Default)
    }

    private fun buildDepartmentTree(
        departments: List<Department>,
        employees: List<Employee>
    ): List<Department> {
        val employeesByDepartment = employees.groupBy { it.departmentId }

        fun build(parentNo: Int): List<Department> {
            return departments
                .filter { it.parentNo == parentNo }
                .sortedWith(compareBy<Department> { it.sortNo }.thenBy { it.name })
                .map { department ->
                    department.copy(
                        users = employeesByDepartment[department.departNo].orEmpty(),
                        childDepartments = build(department.departNo)
                    )
                }
        }

        return build(parentNo = 0)
    }
}
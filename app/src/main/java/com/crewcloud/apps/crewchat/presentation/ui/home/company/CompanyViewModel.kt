package com.crewcloud.apps.crewchat.presentation.ui.home.company

import androidx.lifecycle.viewModelScope
import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStore
import com.crewcloud.apps.crewchat.domain.model.Department
import com.crewcloud.apps.crewchat.domain.model.OrganizationRow
import com.crewcloud.apps.crewchat.domain.model.User
import com.crewcloud.apps.crewchat.domain.repository.DepartmentRepository
import com.crewcloud.apps.crewchat.domain.usecase.GetOrganizationUseCase
import com.crewcloud.apps.crewchat.presentation.core.model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by BM Anderson on 6/7/26.
 */
@HiltViewModel
class CompanyViewModel @Inject constructor(
    organizationUseCase: GetOrganizationUseCase,
    private val departmentRepository: DepartmentRepository,
) : BaseViewModel() {

    val organizationRows = organizationUseCase().map {
        it.toOrganizationRows()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiEvent = Channel<CompanyEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onDepartmentClick(departNo: Int, isExpanded: Boolean) = viewModelScope.launch {
        departmentRepository.updateExpanded(departNo, isExpanded)
    }

    fun checkOrganization() = viewModelScope.launch(Dispatchers.IO) {
        if (departmentRepository.hasDepartment()) {
            if (!departmentRepository.hasEmployees()) {
                departmentRepository.getAllEmployeesFromDepartments()
            } else {

            }
        } else {
            departmentRepository.getAllDepartment()
        }
    }


    private fun List<Department>.toOrganizationRows(
        level: Int = 0
    ): List<OrganizationRow> {
        return flatMap { department ->
            buildList {
                add(
                    OrganizationRow.DepartmentRow(
                        department = department,
                        level = level
                    )
                )

                if (department.isExpanded) {
                    department.users.forEach { employee ->
                        add(
                            OrganizationRow.EmployeeRow(
                                employee = employee,
                                level = level + 1
                            )
                        )
                    }

                    addAll(
                        department.childDepartments.toOrganizationRows(
                            level = level + 1
                        )
                    )
                }
            }
        }
    }
}
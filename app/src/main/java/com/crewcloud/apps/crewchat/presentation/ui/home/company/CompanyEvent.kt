package com.crewcloud.apps.crewchat.presentation.ui.home.company

/**
 * Created by BM Anderson on 8/7/26.
 */
sealed interface CompanyEvent {
    object GetDepartmentsFormServer: CompanyEvent
    object GetEmployeesFormServer: CompanyEvent
}

package com.crewcloud.apps.crewchat.presentation.ui.home.company

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.FolderCopy
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crewcloud.apps.crewchat.R
import com.crewcloud.apps.crewchat.domain.model.Department
import com.crewcloud.apps.crewchat.domain.model.Employee
import com.crewcloud.apps.crewchat.domain.model.OrganizationRow
import com.crewcloud.apps.crewchat.presentation.core.component.DazoneImage
import com.crewcloud.apps.crewchat.presentation.core.component.LoadingDialog
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorPrimary

/**
 * Created by BM Anderson on 6/7/26.
 */
@Composable
fun CompanyScreen(

) {
    val viewModel: CompanyViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val organizationState by viewModel.organizationRows.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.checkOrganization()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("CrewChat", modifier = Modifier.weight(1f))
            Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(24.dp))
            Icon(Icons.Default.Addchart, contentDescription = null, modifier = Modifier.size(24.dp))
        }

        LazyColumn() {
            items(items = organizationState, key = { row ->
                when (row) {
                    is OrganizationRow.DepartmentRow -> row.department.departNo
                    is OrganizationRow.EmployeeRow -> row.employee.username
                }
            }) { row ->
                when (row) {
                    is OrganizationRow.DepartmentRow -> {
                        DepartmentItem(
                            department = row.department,
                            level = row.level,
                            onClick = {
                                viewModel.onDepartmentClick(
                                    row.department.departNo,
                                    !row.department.isExpanded
                                )
                            }
                        )
                    }

                    is OrganizationRow.EmployeeRow -> {
                        EmployeeItem(
                            employee = row.employee,
                            level = row.level
                        )
                    }
                }
            }
        }
    }

    if (uiState.isLoading) {
        LoadingDialog()
    }
}

@Composable
fun DepartmentItem(
    department: Department,
    level: Int,
    onClick: () -> Unit
) {
    Row(modifier = Modifier
        .padding(start = (level * 16).dp)
        .padding(vertical = 4.dp)
        .clickable {
            onClick.invoke()
        }, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            if (department.isExpanded) Icons.Default.FolderOpen else Icons.Default.FolderCopy,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            department.name, style = TextStyle(
                fontSize = 14.sp,
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            if (!department.isExpanded) Icons.Default.KeyboardArrowRight else Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun EmployeeItem(employee: Employee, level: Int) {
    Row(modifier = Modifier
        .padding(start = (level * 16).dp)
        .padding(vertical = 8.dp)) {
        DazoneImage(
            imageUrl = employee.avatar, modifier = Modifier
                .size(42.dp)
                .clip(
                    RoundedCornerShape(6.dp)
                )
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    employee.displayName, style = TextStyle(
                        fontSize = 14.sp,
                    )
                )

                if (employee.phoneNumber.isNotBlank()) {
                    Icon(
                        painterResource(R.drawable.ic_phone),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = ColorPrimary
                    )
                    Text(
                        employee.phoneNumber, style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )
                }
            }
        }
    }
}
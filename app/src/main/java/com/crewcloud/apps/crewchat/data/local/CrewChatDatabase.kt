package com.crewcloud.apps.crewchat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crewcloud.apps.crewchat.data.local.converter.CompanyLocationConverters
import com.crewcloud.apps.crewchat.data.local.dao.ChattingDao
import com.crewcloud.apps.crewchat.data.local.dao.DepartmentDao
import com.crewcloud.apps.crewchat.data.local.dao.EmployeeDao
import com.crewcloud.apps.crewchat.data.local.dao.UserDao
import com.crewcloud.apps.crewchat.data.local.entity.ChattingEntity
import com.crewcloud.apps.crewchat.data.local.entity.DepartmentEntity
import com.crewcloud.apps.crewchat.data.local.entity.EmployeeEntity
import com.crewcloud.apps.crewchat.data.local.entity.UserEntity

/**
 * Created by BM Anderson on 2/7/26.
 */
@Database(
    entities = [
        UserEntity::class,
        DepartmentEntity::class,
        EmployeeEntity::class,
        ChattingEntity::class,
    ],
    version = 5,
    exportSchema = true
)
@TypeConverters(CompanyLocationConverters::class)
abstract class CrewChatDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao

    abstract fun chattingDao(): ChattingDao
    abstract fun departmentDao(): DepartmentDao

    abstract fun employeeDao(): EmployeeDao
}

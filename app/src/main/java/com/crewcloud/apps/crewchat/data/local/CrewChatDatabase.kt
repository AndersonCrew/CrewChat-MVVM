package com.crewcloud.apps.crewchat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crewcloud.apps.crewchat.data.local.converter.CompanyLocationConverters
import com.crewcloud.apps.crewchat.data.local.dao.UserDao
import com.crewcloud.apps.crewchat.data.local.entity.UserEntity

/**
 * Created by BM Anderson on 2/7/26.
 */
@Database(entities = [UserEntity::class], version = 1, exportSchema = true)
@TypeConverters(CompanyLocationConverters::class)
abstract class CrewChatDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}
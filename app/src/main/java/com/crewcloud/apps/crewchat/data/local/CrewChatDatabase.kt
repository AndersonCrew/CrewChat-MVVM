package com.crewcloud.apps.crewchat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crewcloud.apps.crewchat.data.local.entity.UserEntity

/**
 * Created by BM Anderson on 2/7/26.
 */
@Database(entities = [UserEntity::class], version = 1, exportSchema = true)
abstract class CrewChatDatabase : RoomDatabase(){

}
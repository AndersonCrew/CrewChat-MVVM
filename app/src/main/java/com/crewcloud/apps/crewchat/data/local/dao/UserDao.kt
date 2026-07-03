package com.crewcloud.apps.crewchat.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.crewcloud.apps.crewchat.data.local.entity.UserEntity

/**
 * Created by BM Anderson on 3/7/26.
 */
@Dao
interface UserDao {
    @Upsert
    suspend fun saveUser(user: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity?
}
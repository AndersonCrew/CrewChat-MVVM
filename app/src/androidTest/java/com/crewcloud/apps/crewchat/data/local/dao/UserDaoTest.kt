package com.crewcloud.apps.crewchat.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.crewcloud.apps.crewchat.data.local.CrewChatDatabase
import com.crewcloud.apps.crewchat.data.local.entity.UserEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

/**
 * Created by BM Anderson on 4/7/26.
 */
@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var db: CrewChatDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            CrewChatDatabase::class.java,
        ).allowMainThreadQueries().build()

        userDao = db.userDao()
    }

    @Test
    fun saveUser_returnUserSaved() = runTest {
        val user = UserEntity(
            userId = "1",
            fullName = "Anderson",
            id = 1,
            avatarUrl = "url",
            permissionType = 1,
            companyName = "dazone",
            mailAddress = "anhtam@gmail.com",
            companyLocations = listOf(),
            companyNo = 1
        )

        userDao.saveUser(user)
        val result = userDao.getUser()
        assertThat(result).isEqualTo(user)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
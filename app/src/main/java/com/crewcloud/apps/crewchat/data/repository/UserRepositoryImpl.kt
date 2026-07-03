package com.crewcloud.apps.crewchat.data.repository

import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStore
import com.crewcloud.apps.crewchat.data.local.dao.UserDao
import com.crewcloud.apps.crewchat.data.mapper.toDomain
import com.crewcloud.apps.crewchat.data.mapper.toEntity
import com.crewcloud.apps.crewchat.domain.model.User
import com.crewcloud.apps.crewchat.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by BM Anderson on 3/7/26.
 */
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val appPreferenceDataStore: AppPreferenceDataStore
) : UserRepository {
    override suspend fun saveUser(user: User) {
        userDao.saveUser(user.toEntity())
    }

    override suspend fun getUser(): User? {
        return userDao.getUser()?.toDomain()
    }

    override suspend fun getDomain(): String? {
        return appPreferenceDataStore.getDomain()
    }
}
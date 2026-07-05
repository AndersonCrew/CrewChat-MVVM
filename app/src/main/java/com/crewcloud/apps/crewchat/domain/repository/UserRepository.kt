package com.crewcloud.apps.crewchat.domain.repository

import com.crewcloud.apps.crewchat.domain.model.User

/**
 * Created by BM Anderson on 3/7/26.
 */
interface UserRepository {
    suspend fun saveUser(user: User)

    suspend fun clearUser()
    suspend fun getUser(): User?

    suspend fun getDomain(): String?
}
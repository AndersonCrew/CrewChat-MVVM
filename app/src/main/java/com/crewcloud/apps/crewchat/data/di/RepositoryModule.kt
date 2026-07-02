package com.crewcloud.apps.crewchat.data.di

import com.crewcloud.apps.crewchat.data.repository.AuthRepositoryImpl
import com.crewcloud.apps.crewchat.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by BM Anderson on 2/7/26.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}
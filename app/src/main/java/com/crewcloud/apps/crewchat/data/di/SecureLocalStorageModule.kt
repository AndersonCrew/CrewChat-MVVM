package com.crewcloud.apps.crewchat.data.di

import com.crewcloud.apps.crewchat.data.local.SecureLocalStorage
import com.crewcloud.apps.crewchat.data.local.SecureLocalStorageImpl
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
interface SecureLocalStorageModule {

    @Binds
    @Singleton
    abstract fun bindSecureLocalStorage(
        impl: SecureLocalStorageImpl
    ): SecureLocalStorage
}
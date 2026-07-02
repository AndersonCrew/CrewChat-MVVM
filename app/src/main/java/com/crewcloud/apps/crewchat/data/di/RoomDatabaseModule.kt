package com.crewcloud.apps.crewchat.data.di

import android.content.Context
import androidx.room.Room
import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStore
import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStoreImpl
import com.crewcloud.apps.crewchat.data.local.CrewChatDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by BM Anderson on 2/7/26.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): CrewChatDatabase {
        return Room.databaseBuilder(context, CrewChatDatabase::class.java, "CrewChat-Room-DB")
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface AppDataStore {

    @Binds
    @Singleton
    abstract fun provideAppPreferenceDataStore(
        impl: AppPreferenceDataStoreImpl
    ): AppPreferenceDataStore
}
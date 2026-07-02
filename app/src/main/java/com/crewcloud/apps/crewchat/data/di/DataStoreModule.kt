package com.crewcloud.apps.crewchat.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
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
object DataStoreModule {

    private const val DAZONE_PREFERENCES_NAME = "dazone-local-data-store"
    @Provides
    @Singleton
    fun getDataStore(@ApplicationContext context: Context) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create (
            produceFile = {context.preferencesDataStoreFile(DAZONE_PREFERENCES_NAME)}
        )
    }
}
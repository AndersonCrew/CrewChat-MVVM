package com.crewcloud.apps.crewchat.data.di

import android.app.Activity
import androidx.activity.ComponentActivity
import com.crewcloud.apps.crewchat.data.repository.AndroidPermissionTracker
import com.crewcloud.apps.crewchat.domain.repository.PermissionTracker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

/**
 * Created by BM Anderson on 5/7/26.
 */
@Module
@InstallIn(ActivityComponent::class)
object PermissionModule {

    @Provides
    @ActivityScoped
    fun providePermissionTracker(activity: Activity): PermissionTracker {
        return AndroidPermissionTracker(activity as ComponentActivity)
    }
}
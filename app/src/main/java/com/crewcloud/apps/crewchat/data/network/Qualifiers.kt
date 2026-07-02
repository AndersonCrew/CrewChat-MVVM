package com.crewcloud.apps.crewchat.data.network

import javax.inject.Qualifier

/**
 * Created by BM Anderson on 2/7/26.
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StaticRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DazoneRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StaticOkhttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DazoneOkhttp
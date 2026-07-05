package com.crewcloud.apps.crewchat.data.di

import com.crewcloud.apps.crewchat.BuildConfig
import com.crewcloud.apps.crewchat.data.network.api.DazoneApiService
import com.crewcloud.apps.crewchat.data.network.api.StaticApiService
import com.crewcloud.apps.crewchat.data.network.AuthInterceptor
import com.crewcloud.apps.crewchat.data.network.DazoneOkhttp
import com.crewcloud.apps.crewchat.data.network.DazoneRetrofit
import com.crewcloud.apps.crewchat.data.network.StaticOkhttp
import com.crewcloud.apps.crewchat.data.network.StaticRetrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by BM Anderson on 2/7/26.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    @DazoneOkhttp
    fun provideDazoneOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor).addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    @StaticOkhttp
    fun provideStaticOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    @StaticRetrofit
    fun provideStaticRetrofit(
        moshi: Moshi, @StaticOkhttp okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://mobileupdate.crewcloud.net/WebServiceMobile.asmx/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    @DazoneRetrofit
    fun provideDazoneRetrofit(
        moshi: Moshi,
        @DazoneOkhttp okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://localhost.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideStaticApi(
        @StaticRetrofit retrofit: Retrofit
    ): StaticApiService {
        return retrofit.create(StaticApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDazoneApi(
        @DazoneRetrofit retrofit: Retrofit
    ): DazoneApiService {
        return retrofit.create(DazoneApiService::class.java)
    }
}
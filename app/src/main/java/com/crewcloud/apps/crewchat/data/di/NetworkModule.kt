package com.crewcloud.apps.crewchat.data.di

import com.crewcloud.apps.crewchat.BuildConfig
import com.crewcloud.apps.crewchat.data.network.api.DazoneApiService
import com.crewcloud.apps.crewchat.data.network.api.StaticApiService
import com.crewcloud.apps.crewchat.data.network.AuthInterceptor
import com.crewcloud.apps.crewchat.data.network.DazoneOkhttp
import com.crewcloud.apps.crewchat.data.network.DazoneRetrofit
import com.crewcloud.apps.crewchat.data.network.StaticOkhttp
import com.crewcloud.apps.crewchat.data.network.StaticRetrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by BM Anderson on 2/7/26.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val jsonConfig = Json {
        ignoreUnknownKeys = true // Rất quan trọng! API thừa trường so với DTO cũng không bị crash
        coerceInputValues =
            true // Nếu API trả về null ở trường không cho phép null, nó tự lấy giá trị mặc định
        encodeDefaults =
            true    // Tự động thêm các giá trị mặc định vào chuỗi JSON khi convert xuôi
    }

    // 2. Định nghĩa Content-Type là application/json
    val contentType = "application/json".toMediaType()

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
        @StaticOkhttp okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://mobileupdate.crewcloud.net/WebServiceMobile.asmx/")
            .addConverterFactory(jsonConfig.asConverterFactory(contentType))
            .client(okHttpClient).build()
    }

    @Provides
    @Singleton
    @DazoneRetrofit
    fun provideDazoneRetrofit(
        @DazoneOkhttp okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://localhost.com/")
            .addConverterFactory(jsonConfig.asConverterFactory(contentType)).client(okHttpClient)
            .build()
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
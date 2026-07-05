package com.crewcloud.apps.crewchat.data.network

import android.util.Log
import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by BM Anderson on 2/7/26.
 */
class AuthInterceptor @Inject constructor(
    private val appPreferenceDataStore: AppPreferenceDataStore
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newBaseUrlString = runBlocking { appPreferenceDataStore.getBaseUrl() }
        val newHttpUrl = newBaseUrlString?.toHttpUrlOrNull()

        return if (newHttpUrl != null) {
            val newUrl = originalRequest.url.newBuilder()
                .scheme(newHttpUrl.scheme)
                .host(newHttpUrl.host)
                .port(newHttpUrl.port)
                .build()

            val newRequest = originalRequest.newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}
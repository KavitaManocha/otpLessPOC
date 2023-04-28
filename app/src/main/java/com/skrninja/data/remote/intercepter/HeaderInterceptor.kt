package com.skrninja.data.remote.intercepter

import com.skrninja.data.local.SessionManager
import com.skrninja.utilities.DeviceManager
import okhttp3.Interceptor
import okhttp3.Response

private const val contentType = "Content-Type"
private const val contentTypeValue = "application/json"
private const val PLATFORM = "Platform"
private const val deviceTypeValue = "Android"
const val APP_ID = "ApplicationId"
private const val DEVICE_ID = "DeviceId"
const val TOKEN = "authorization"

class HeaderInterceptor(
    private val sessionManager: SessionManager,
    private val deviceManger: DeviceManager
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val appId = sessionManager.getAppId().toString()
            val token = sessionManager.getAuthToken().toString()
            val original = chain.request()
            val request = original.newBuilder()
                .header(contentType, contentTypeValue)
                .addHeader(PLATFORM, deviceTypeValue)
                .addHeader(APP_ID, appId)
                .addHeader(DEVICE_ID, deviceManger.getDeviceId())
                .addHeader(TOKEN, token)
                .method(original.method, original.body)
                .build()
            return chain.proceed(request)
        }
    }
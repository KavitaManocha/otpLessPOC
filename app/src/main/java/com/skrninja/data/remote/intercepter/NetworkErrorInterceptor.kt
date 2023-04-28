package com.skrninja.data.remote.intercepter

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.skrninja.data.local.SessionManager
import com.skrninja.data.remote.GlobalNavigator
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketTimeoutException

class NetworkErrorInterceptor(val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            val bodyString = response.body?.string()

            val jsonObject = Gson().fromJson<JsonObject>(bodyString, JsonObject::class.java)
            if (jsonObject.has("success")) {
                val isSuccess = jsonObject.get("success").asBoolean
                if (!isSuccess) {
                    val isException = jsonObject.has("exception")
                    if (isException) {
                        val exception = jsonObject.get("exception").asJsonObject//.has()
                        val code = exception.get("Code").asInt
                        if (code == 120) {
                            GlobalNavigator.logout()
                        } else if (code == 123) {
                            GlobalNavigator.rejected()
                        }
                    }
                }
            }
            return response.newBuilder()
                .body(bodyString?.toResponseBody(response.body?.contentType()))
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
            var msg = ""
            var interceptorCode: Int = 0

            when (e) {
                is SocketTimeoutException -> {
                    msg = "Socket timeout error"
                    interceptorCode = 408
                }
            }
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(interceptorCode)
                .message(msg)
                .body("{${e}}".toResponseBody(null)).build()
        }
    }
}
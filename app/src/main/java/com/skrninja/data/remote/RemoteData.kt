package com.skrninja.data.remote

import com.skrninja.data.Resource
import com.skrninja.data.dto.request.login.LoginRequest
import com.skrninja.data.dto.response.LoginResponse

import com.skrninja.data.error.NETWORK_ERROR
import com.skrninja.data.error.NO_INTERNET_CONNECTION
import com.skrninja.data.local.SessionManager
import com.skrninja.data.remote.service.ApiService
import com.skrninja.utilities.NetworkConnectivity
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Sunil
 */

class RemoteData @Inject constructor(
    serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity,
    private val sessionManager: SessionManager
) : RemoteDataSource {

    private val apiInterface = serviceGenerator.createService(ApiService::class.java)


    private suspend fun processCall(responseCall: suspend () -> retrofit2.Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }


    override suspend fun doRemoteLogIn(
        logInRequest: LoginRequest
    ): Resource<LoginResponse> {
        val response = apiInterface.login(logInRequest)
        return when (val response1 = this.processCall { response }) {
            is LoginResponse -> {
                Resource.Success(data = response1)
            }
            else -> {
                Resource.DataError(errorCode = response1 as Int)
            }
        }
    }

}

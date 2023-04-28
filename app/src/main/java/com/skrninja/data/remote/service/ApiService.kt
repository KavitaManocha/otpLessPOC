package com.skrninja.data.remote.service


import com.skrninja.data.dto.request.login.LoginRequest
import com.skrninja.data.dto.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Sunil
 */

interface ApiService {


    // for login
    @POST("login")
    suspend fun login(
        @Body logInRequest: LoginRequest
    ): Response<LoginResponse>



}

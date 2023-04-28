package com.skrninja.data.remote

import com.skrninja.data.Resource
import com.skrninja.data.dto.request.login.LoginRequest
import com.skrninja.data.dto.response.LoginResponse


/**
 * Created by Sunil
 */

internal interface RemoteDataSource {

    suspend fun doRemoteLogIn(logInRequest: LoginRequest): Resource<LoginResponse>


}

package com.skrninja.data.error.mapper

import android.content.Context
import com.skrninja.data.error.*
import com.skrninja.mvvm.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) :
    ErrorMapperSource {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(SERVER_ERROR, getErrorString(R.string.server_error)),
            Pair(UNKNOWN_HOST_EXCEPTION,  getErrorString(R.string.unknown_host_exception)),
            Pair(SOCKET_TIMEOUT_EXCEPTION, getErrorString(R.string.socket_timeout_exception)),
            Pair(TIMEOUT_ERROR, getErrorString(R.string.socket_timeout_exception))
        ).withDefault {
            getErrorString(R.string.network_error)
        }
}

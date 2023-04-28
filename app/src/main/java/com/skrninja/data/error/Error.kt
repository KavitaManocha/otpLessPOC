package com.skrninja.data.error

/**
 * Created by Sunil
 */

class Error(val code: Int, val description: String) {
    constructor(exception: Exception) : this(
        code = DEFAULT_ERROR, description = exception.message
            ?: ""
    )
}

const val NO_INTERNET_CONNECTION = -101
const val UNKNOWN_HOST_EXCEPTION = -102
const val SOCKET_TIMEOUT_EXCEPTION = -103
const val SERVER_ERROR = 500
const val TIMEOUT_ERROR = 408
const val NETWORK_ERROR = -2
const val DEFAULT_ERROR = -3



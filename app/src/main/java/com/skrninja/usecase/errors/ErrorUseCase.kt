package com.skrninja.usecase.errors

import com.skrninja.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}

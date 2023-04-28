package com.skrninja.data

import androidx.lifecycle.MutableLiveData
import com.skrninja.data.enums.OperationErrorType
import com.skrninja.utilities.OperationError
import com.skrninja.utilities.SingleLiveEvent

open class BaseRepository {

    /**
     * Dedicated live data object which use to communicate error details occurs in repository
     * layer to upper layers
     */
    private val operationErrorLiveDate = MutableLiveData<SingleLiveEvent<OperationError>>()

    /**
     * function use to create the [OperationError] object when network call connected the server
     * endpoint successfully but from server trowing error generated by backend logic.
     *
     * @param errorMessage: error message send from the backend response
     * @param fieldErrors: if it is a form submission operation and backend inform validation
     *                     issues in the given data it is those data are added here with the key
     *                     value to identify the field.
     * @param errorId : This parameter is only use if there are multiple database or network operation
     *                  occurs in the same screen and when it's errors need to be handle separately
     */
    fun responseError(
        errorMessage: String,
        fieldErrors: Map<String, Any> = mapOf(),
        errorId: Int = 1) {

        val operationError = OperationError
            .Builder(OperationErrorType.RESPONSE_ERROR)
            .errorId(errorId)
            .messageTitle("Response Error")
            .message(errorMessage)
            .fieldError(fieldErrors)
            .build()

        operationErrorLiveDate.value = SingleLiveEvent(operationError)
    }

    /**
     * function use to create the [OperationError] object when network call fail
     * to connected the server and give success result.
     *
     * @param errorMessage: server error localized message
     * @param errorId : This parameter is only use if there are multiple database or network operation
     *                  occurs in the same screen and when it's errors need to be handle separately
     *
     */
    fun connectionError(errorMessage: String, errorId: Int = 1) {

        val operationError = OperationError
            .Builder(OperationErrorType.CONNECTION_ERROR)
            .errorId(errorId)
            .messageTitle("Connection Error")
            .message(errorMessage)
            .build()

        operationErrorLiveDate.value = SingleLiveEvent(operationError)
    }

    /**
     * function use to create the [OperationError] object when network call cannot be made and
     * resolve due to an error in code or json phasing
     *
     * @param errorId : This parameter is only use if there are multiple database or network operation
     *                  occurs in the same screen and when it's errors need to be handle separately
     */
    fun processingError(errorId: Int = 1) {

        val operationError = OperationError
            .Builder(OperationErrorType.PROCESSING_ERROR)
            .errorId(errorId)
            .messageTitle("Processing Error")
            .message("Something Went Wrong Please Try again later.")
            .build()

        operationErrorLiveDate.value = SingleLiveEvent(operationError)
    }

    /**
     * function use to create the [OperationError] object when network call failed to connect with
     * the server with in the setup time out value
     *
     * @param errorId : This parameter is only use if there are multiple database or network operation
     *                  occurs in the same screen and when it's errors need to be handle separately
     */
    fun timeoutConnectionError(errorId: Int = 1) {

        val operationError = OperationError
            .Builder(OperationErrorType.CONNECTION_ERROR)
            .errorId(errorId)
            .messageTitle("Connection Error")
            .message("Failed to connect to the server please try again later.")
            .build()

        operationErrorLiveDate.value = SingleLiveEvent(operationError)
    }

    /**
     * function use to create the [OperationError] object when device is not connected to the internet
     *
     * @param errorId : This parameter is only use if there are multiple database or network operation
     *                  occurs in the same screen and when it's errors need to be handle separately
     */
    fun noConnectivityError(errorId: Int = 1) {

        val operationError = OperationError
            .Builder(OperationErrorType.CONNECTION_ERROR)
            .errorId(errorId)
            .messageTitle("Connection Error")
            .message("Device is not connected to the internet. Please check your mobile internet connection.")
            .build()

        operationErrorLiveDate.value = SingleLiveEvent(operationError)
    }
}
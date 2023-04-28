package com.skrninja.data.dto.response

import com.skrninja.data.dto.InputError
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "status")
    val status: Boolean,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val `data`: Data?,
    @Json(name = "errors")
    val errors: List<InputError>?
)
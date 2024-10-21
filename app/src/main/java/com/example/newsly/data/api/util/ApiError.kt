package com.example.newsly.data.api.util

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("code")
    val errorCode: String,
    val message: String
)

fun String.toApiError(): ApiError = Gson().fromJson(this, ApiError::class.java)

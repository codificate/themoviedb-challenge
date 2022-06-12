package com.challenge.themoviedb.data.network.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val password: String,
    @SerializedName("request_token") val request_token: String,
    val username: String
)
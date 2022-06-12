package com.challenge.themoviedb.data.network.response

data class AuthenticationTokenResponse(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)
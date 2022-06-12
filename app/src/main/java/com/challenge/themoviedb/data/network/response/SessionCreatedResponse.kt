package com.challenge.themoviedb.data.network.response

import com.google.gson.annotations.SerializedName

data class SessionCreatedResponse(
    @SerializedName("session_id") val session_id: String,
    val success: Boolean
)
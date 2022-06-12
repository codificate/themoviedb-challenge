package com.challenge.themoviedb.data.network.request

import com.google.gson.annotations.SerializedName

class CreateSessionRequest(@SerializedName("request_token") val request_token: String)
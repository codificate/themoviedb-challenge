package com.challenge.themoviedb.data.api

import com.challenge.themoviedb.BuildConfig
import com.challenge.themoviedb.data.network.request.CreateSessionRequest
import com.challenge.themoviedb.data.network.request.LoginRequest
import com.challenge.themoviedb.data.network.response.AuthenticationTokenResponse
import com.challenge.themoviedb.data.network.response.SessionCreatedResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthenticationApiService {

    @POST("authentication/session/new")
    suspend fun createSession(
        @Path("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Body createSessionRequest: CreateSessionRequest
    ): SessionCreatedResponse

    @GET("authentication/token/new")
    suspend fun createToken(@Path("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): AuthenticationTokenResponse

    @POST("authentication/token/validate_with_login")
    suspend fun validateLogin(
        @Path("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Body loginRequest: LoginRequest
    ): AuthenticationTokenResponse

}
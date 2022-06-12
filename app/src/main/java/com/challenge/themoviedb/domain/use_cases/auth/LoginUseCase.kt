package com.challenge.themoviedb.domain.use_cases.auth

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.api.AuthenticationApiService
import com.challenge.themoviedb.data.network.request.LoginRequest
import com.challenge.themoviedb.data.network.response.AuthenticationTokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val apiService: AuthenticationApiService) {

    operator fun invoke(userName: String, password: String, token: String): Flow<DataResource<AuthenticationTokenResponse>> = flow {
        try {
            val authenticationResponse = apiService.validateLogin(loginRequest = LoginRequest(userName, password, token))
            emit(DataResource.Success(authenticationResponse))
        } catch (e: HttpException) {
            emit(DataResource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(DataResource.Error("Couldn't reach server. Check your internet connection."))
        }
    }.flowOn(Dispatchers.IO)
}
package com.challenge.themoviedb.domain.use_cases.auth

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.api.AuthenticationApiService
import com.challenge.themoviedb.data.network.response.AuthenticationTokenResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RequestTokenUseCase @Inject constructor(private val apiService: AuthenticationApiService) {

    operator fun invoke(): Flow<DataResource<AuthenticationTokenResponse>> = flow {
        try {
            val authenticationResponse = apiService.createToken()
            emit(DataResource.Success(authenticationResponse))
        } catch (e: HttpException) {
            emit(DataResource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(DataResource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
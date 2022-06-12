package com.challenge.themoviedb.domain.use_cases.auth

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.api.AuthenticationApiService
import com.challenge.themoviedb.data.network.request.CreateSessionRequest
import com.challenge.themoviedb.data.network.response.SessionCreatedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(private val apiService: AuthenticationApiService) {

    operator fun invoke(token: String): Flow<DataResource<SessionCreatedResponse>> = flow {
        try {
            val sessionCreated = apiService.createSession(createSessionRequest = CreateSessionRequest(token))
            emit(DataResource.Success(sessionCreated))
        } catch (e: HttpException) {
            emit(DataResource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(DataResource.Error("Couldn't reach server. Check your internet connection."))
        }
    }.flowOn(Dispatchers.IO)
}
package com.challenge.themoviedb.domain.use_cases.auth

import com.challenge.themoviedb.data.api.AuthenticationApiService
import com.challenge.themoviedb.data.network.request.CreateSessionRequest
import com.challenge.themoviedb.data.network.response.SessionCreatedResponse
import com.challenge.themoviedb.domain.DataResource
import com.challenge.themoviedb.domain.HTTP_EXCEPTION_ERROR_MESSAGE
import com.challenge.themoviedb.domain.IOEXCEPTION_ERROR_MESSAGE
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
            emit(DataResource.Error(e.localizedMessage ?: HTTP_EXCEPTION_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(DataResource.Error(IOEXCEPTION_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)
}
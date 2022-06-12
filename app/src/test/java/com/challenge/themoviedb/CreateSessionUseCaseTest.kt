package com.challenge.themoviedb

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.HTTP_EXCEPTION_ERROR_MESSAGE
import com.challenge.themoviedb.data.api.AuthenticationApiService
import com.challenge.themoviedb.data.network.response.SessionCreatedResponse
import com.challenge.themoviedb.domain.use_cases.auth.CreateSessionUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.HttpException
import java.lang.Boolean.TRUE
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(PowerMockRunner::class)
class CreateSessionUseCaseTest {

    private val api = mock<AuthenticationApiService>()
    private val httpException = mock<HttpException>()
    private val useCase = CreateSessionUseCase(api)

    @Test
    fun `WHEN use case is invoked the createSession api call SHOULD return an SessionCreatedResponse`() = runBlocking {
        // GIVEN
        whenever(api.createSession(apiKey = any(), createSessionRequest =  any())).thenReturn(SESSION_RESPONSE)

        // WHEN
        useCase(TOKEN).collect { authentication ->

        // THEN
            assertTrue { authentication is DataResource.Success }
            assertEquals(authentication.data, SESSION_RESPONSE)
        }
    }

    @Test
    fun `WHEN use case is invoked the createSession api call SHOULD throw a HttpException`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(HTTP_ERROR)
        whenever(api.createSession(apiKey = any(), createSessionRequest =  any())).thenThrow(httpException)

        // WHEN
        useCase(TOKEN).collect { authentication ->

            // THEN
            assertTrue { authentication is DataResource.Error }
            assertEquals(authentication.message, HTTP_ERROR)
        }
    }

    @Test
    fun `WHEN use case is invoked the createSession api call SHOULD throw a HTTP ERROR MESSAGE`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(null)
        whenever(api.createSession(apiKey = any(), createSessionRequest =  any())).thenThrow(httpException)

        // WHEN
        useCase(TOKEN).collect { authentication ->

            // THEN
            assertTrue { authentication is DataResource.Error }
            (authentication as DataResource.Error).message?.let {
                assertEquals(it, HTTP_EXCEPTION_ERROR_MESSAGE)
            }
        }
    }

    private companion object {
        const val TOKEN = "TOKEN"
        const val SESSION_ID = "SESSION_ID"
        const val HTTP_ERROR = "HTTP_ERROR"
        val SESSION_RESPONSE = SessionCreatedResponse(SESSION_ID, TRUE)
    }

}
package com.challenge.themoviedb

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.HTTP_EXCEPTION_ERROR_MESSAGE
import com.challenge.themoviedb.data.api.AuthenticationApiService
import com.challenge.themoviedb.data.network.response.AuthenticationTokenResponse
import com.challenge.themoviedb.domain.use_cases.auth.LoginUseCase
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
class LoginUseCaseTest {

    private val api = mock<AuthenticationApiService>()
    private val httpException = mock<HttpException>()
    private val useCase = LoginUseCase(api)

    @Test
    fun `WHEN use case is invoked the validateLogin api call SHOULD return an AuthenticationResponse`() = runBlocking {
        // GIVEN
        whenever(api.validateLogin(apiKey = any(), loginRequest =  any())).thenReturn(TOKEN_RESPONSE)

        // WHEN
        useCase(USER_NAME, PASSWORD, TOKEN).collect { authentication ->

        // THEN
            assertTrue { authentication is DataResource.Success }
            assertEquals(authentication.data, TOKEN_RESPONSE)
        }
    }

    @Test
    fun `WHEN use case is invoked the validateLogin api call SHOULD throw a HttpException`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(HTTP_ERROR)
        whenever(api.validateLogin(apiKey = any(), loginRequest =  any())).thenThrow(httpException)

        // WHEN
        useCase(USER_NAME, PASSWORD, TOKEN).collect { authentication ->

            // THEN
            assertTrue { authentication is DataResource.Error }
            assertEquals(authentication.message, HTTP_ERROR)
        }
    }

    @Test
    fun `WHEN use case is invoked the validateLogin api call SHOULD throw a HTTP ERROR MESSAGE`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(null)
        whenever(api.validateLogin(apiKey = any(), loginRequest =  any())).thenThrow(httpException)

        // WHEN
        useCase(USER_NAME, PASSWORD, TOKEN).collect { authentication ->

            // THEN
            assertTrue { authentication is DataResource.Error }
            (authentication as DataResource.Error).message?.let {
                assertEquals(it, HTTP_EXCEPTION_ERROR_MESSAGE)
            }
        }
    }

    private companion object {
        const val EXPIRES_AT = "EXPIRES_AT"
        const val REQUEST_TOKEN = "REQUEST_TOKEN"
        const val HTTP_ERROR = "HTTP_ERROR"
        const val USER_NAME = "USER_NAME"
        const val PASSWORD = "PASSWORD"
        const val TOKEN = "TOKEN"
        val TOKEN_RESPONSE = AuthenticationTokenResponse(EXPIRES_AT, REQUEST_TOKEN, TRUE)
    }

}
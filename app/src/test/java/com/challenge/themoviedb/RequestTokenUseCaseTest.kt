package com.challenge.themoviedb

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.HTTP_EXCEPTION_ERROR_MESSAGE
import com.challenge.themoviedb.data.api.AuthenticationApiService
import com.challenge.themoviedb.data.network.response.AuthenticationTokenResponse
import com.challenge.themoviedb.domain.use_cases.auth.RequestTokenUseCase
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
class RequestTokenUseCaseTest {

    private val api = mock<AuthenticationApiService>()
    private val httpException = mock<HttpException>()
    private val useCase = RequestTokenUseCase(api)

    @Test
    fun `WHEN use case is invoked the api service SHOULD return an AuthenticationResponse`() = runBlocking {
        // GIVEN
        whenever(api.createToken()).thenReturn(TOKEN_RESPONSE)

        // WHEN
        useCase().collect { authentication ->

        // THEN
            assertTrue { authentication is DataResource.Success }
            assertEquals(authentication.data, TOKEN_RESPONSE)
        }
    }

    @Test
    fun `WHEN use case is invoked the api service SHOULD return an HttpException`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(HTTP_ERROR)
        whenever(api.createToken()).thenThrow(httpException)

        // WHEN
        useCase().collect { authentication ->

            // THEN
            assertTrue { authentication is DataResource.Error }
            assertEquals(authentication.message, HTTP_ERROR)
        }
    }

    @Test
    fun `WHEN use case is invoked the api service SHOULD validate the HTTP ERROR MESSAGE`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(null)
        whenever(api.createToken()).thenThrow(httpException)

        // WHEN
        useCase().collect { authentication ->

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
        val TOKEN_RESPONSE = AuthenticationTokenResponse(EXPIRES_AT, REQUEST_TOKEN, TRUE)
    }

}
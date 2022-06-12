package com.challenge.themoviedb

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.HTTP_EXCEPTION_ERROR_MESSAGE
import com.challenge.themoviedb.data.api.MoviesApiService
import com.challenge.themoviedb.data.network.response.GeneresListResponse
import com.challenge.themoviedb.data.network.response.GenreResponse
import com.challenge.themoviedb.domain.model.Genre
import com.challenge.themoviedb.domain.use_cases.movies.FetchGeneresUseCase
import org.junit.runner.RunWith
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.HttpException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(PowerMockRunner::class)
class FetchGeneresUseCaseTest {

    private val api = mock<MoviesApiService>()
    private val httpException = mock<HttpException>()
    private val useCase = FetchGeneresUseCase(api)

    @Test
    fun `WHEN use case is invoked the api service SHOULD return a GeneresListResponse`() = runBlocking {
        // GIVEN
        whenever(api.fetchGeneres()).thenReturn(GENRE_LIST_RESPONSE)

        // WHEN
        useCase().collect { genres ->
            // THEN
            assertTrue { genres is DataResource.Success }
            assertTrue { genres.data is List<Genre>}
            genres.data?.let { list -> assertTrue { list.isNotEmpty() } }
        }
    }

    @Test
    fun `WHEN use case is invoked the api service SHOULD return an HttpException`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(HTTP_ERROR)
        whenever(api.fetchGeneres()).thenThrow(httpException)

        // WHEN
        useCase().collect { genres ->

            // THEN
            assertTrue { genres is DataResource.Error }
            assertEquals(genres.message, HTTP_ERROR)
        }
    }

    @Test
    fun `WHEN use case is invoked the api service SHOULD validate the HTTP ERROR MESSAGE`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(null)
        whenever(api.fetchGeneres()).thenThrow(httpException)

        // WHEN
        useCase().collect { genres ->

            // THEN
            assertTrue { genres is DataResource.Error }
            (genres as DataResource.Error).message?.let {
                assertEquals(it, HTTP_EXCEPTION_ERROR_MESSAGE)
            }
        }
    }

    private companion object {
        const val HTTP_ERROR = "HTTP_ERROR"
        val GENRE_ID = 1
        val GENRE_NAME = "GENRE_NAME"
        val GENRE_RESPONSE = GenreResponse(GENRE_ID, GENRE_NAME)
        val GENRE_LIST_RESPONSE = GeneresListResponse(listOf(GENRE_RESPONSE))
    }
}
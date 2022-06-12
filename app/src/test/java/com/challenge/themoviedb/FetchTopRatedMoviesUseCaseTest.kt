package com.challenge.themoviedb

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.HTTP_EXCEPTION_ERROR_MESSAGE
import com.challenge.themoviedb.data.api.MoviesApiService
import com.challenge.themoviedb.data.network.response.MovieResponse
import com.challenge.themoviedb.data.network.response.MoviesListResponse
import com.challenge.themoviedb.domain.model.MoviesPages
import com.challenge.themoviedb.domain.use_cases.movies.FetchTopRatedMoviesUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.HttpException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(PowerMockRunner::class)
class FetchTopRatedMoviesUseCaseTest {

    private val api = mock<MoviesApiService>()
    private val httpException = mock<HttpException>()
    private val useCase = FetchTopRatedMoviesUseCase(api)

    @Test
    fun `WHEN use case is invoked the api service SHOULD return a MoviesListResponse`() = runBlocking {
        // GIVEN
        whenever(api.fetchTopRatedMovies()).thenReturn(MOVIE_LIST_RESPONSE)

        // WHEN
        useCase().collect { movies ->
            // THEN
            assertTrue { movies is DataResource.Success }
            assertTrue { movies.data is MoviesPages }
            movies.data?.let { moviePages ->
                assertTrue { moviePages.page == PAGE }
                assertTrue { moviePages.total_pages == TOTAL_PAGES }
                assertTrue { moviePages.total_results == TOTAL_RESULT }
                assertTrue { moviePages.movies.isNotEmpty() }
                assertTrue { moviePages.movies.size == 1 }
            }
        }
    }

    @Test
    fun `WHEN use case is invoked the api service SHOULD throw a HttpException`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(HTTP_ERROR)
        whenever(api.fetchTopRatedMovies()).thenThrow(httpException)

        // WHEN
        useCase().collect { movies ->

            // THEN
            assertTrue { movies is DataResource.Error }
            assertEquals(movies.message, HTTP_ERROR)
        }
    }

    @Test
    fun `WHEN use case is invoked the fetchTopRatedMovies api call SHOULD throw a HTTP ERROR MESSAGE`() = runBlocking {
        // GIVEN
        whenever(httpException.localizedMessage).thenReturn(null)
        whenever(api.fetchTopRatedMovies()).thenThrow(httpException)

        // WHEN
        useCase().collect { movies ->

            // THEN
            assertTrue { movies is DataResource.Error }
            (movies as DataResource.Error).message?.let {
                assertEquals(it, HTTP_EXCEPTION_ERROR_MESSAGE)
            }
        }
    }

    private companion object {
        const val HTTP_ERROR = "HTTP_ERROR"
        val MOVIE_ID = 1
        val MOVIE_TITLE = "MOVIE_TITLE"
        val MOVIE_POSTER_PATH = "MOVIE_POSTER_PATH"
        val PAGE = 1
        val TOTAL_PAGES = 1
        val TOTAL_RESULT = 1
        val MOVIE_RESPONSE = generateMovieMock()
        val MOVIE_LIST_RESPONSE = MoviesListResponse(PAGE, listOf(MOVIE_RESPONSE), TOTAL_PAGES, TOTAL_RESULT)

        fun generateMovieMock(): MovieResponse {
            val movieMock = mock<MovieResponse>()
            whenever(movieMock.id).thenReturn(MOVIE_ID)
            whenever(movieMock.title).thenReturn(MOVIE_TITLE)
            whenever(movieMock.poster_path).thenReturn(MOVIE_POSTER_PATH)
            return movieMock
        }
    }
}
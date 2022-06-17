package com.challenge.themoviedb.domain.use_cases.movies

import com.challenge.themoviedb.data.api.MoviesApiService
import com.challenge.themoviedb.data.network.response.toMoviesPage
import com.challenge.themoviedb.domain.DataResource
import com.challenge.themoviedb.domain.HTTP_EXCEPTION_ERROR_MESSAGE
import com.challenge.themoviedb.domain.IOEXCEPTION_ERROR_MESSAGE
import com.challenge.themoviedb.domain.model.MoviesPages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FetchPopularMoviesUseCase @Inject constructor(private val apiService: MoviesApiService) {

    operator fun invoke(): Flow<DataResource<MoviesPages>> = flow {
        try {
            val popularMoviesList = apiService.fetchPopularMovies()
            emit(DataResource.Success(popularMoviesList.toMoviesPage()))
        } catch (e: HttpException) {
            emit(DataResource.Error(e.localizedMessage ?: HTTP_EXCEPTION_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(DataResource.Error(IOEXCEPTION_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)
}
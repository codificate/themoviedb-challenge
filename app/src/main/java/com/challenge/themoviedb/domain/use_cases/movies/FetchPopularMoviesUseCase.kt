package com.challenge.themoviedb.domain.use_cases.movies

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.api.MoviesApiService
import com.challenge.themoviedb.domain.model.MoviesPages
import com.challenge.themoviedb.domain.model.toMoviesPage
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
            emit(DataResource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(DataResource.Error("Couldn't reach server. Check your internet connection."))
        }
    }.flowOn(Dispatchers.IO)
}
package com.challenge.themoviedb.domain.use_cases.movies

import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.data.api.MoviesApiService
import com.challenge.themoviedb.domain.model.Genre
import com.challenge.themoviedb.domain.model.toGenre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FetchGeneresUseCase @Inject constructor(private val apiService: MoviesApiService) {

    operator fun invoke(): Flow<DataResource<List<Genre>>> = flow {
        try {
            val generesListResponse = apiService.fetchGeneres()
            emit(DataResource.Success(generesListResponse.genres.map { genreResponse -> genreResponse.toGenre() }))
        } catch (e: HttpException) {
            emit(DataResource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(DataResource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
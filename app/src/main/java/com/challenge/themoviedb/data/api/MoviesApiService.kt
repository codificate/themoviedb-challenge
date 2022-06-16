package com.challenge.themoviedb.data.api

import com.challenge.themoviedb.BuildConfig
import com.challenge.themoviedb.data.network.response.GeneresListResponse
import com.challenge.themoviedb.data.network.response.MoviesListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("genre/movie/list")
    suspend fun fetchGeneres(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): GeneresListResponse

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MoviesListResponse

    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MoviesListResponse

}
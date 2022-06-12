package com.challenge.themoviedb.domain.model

import android.os.Parcelable
import com.challenge.themoviedb.data.network.response.MoviesListResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesPages(
    val page: Int,
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
): Parcelable

fun MoviesListResponse.toMoviesPage(): MoviesPages {
    return MoviesPages(
        this.page,
        this.results.map { movieResponse -> movieResponse.toMovie() },
        this.total_pages,
        this.total_results
    )
}
package com.challenge.themoviedb.data.network.response

import com.challenge.themoviedb.domain.model.MoviesPages

data class MoviesListResponse(
    val page: Int,
    val results: List<MovieResponse>,
    val total_pages: Int,
    val total_results: Int
)

fun MoviesListResponse.toMoviesPage(): MoviesPages {
    return MoviesPages(
        this.page,
        this.results.map { movieResponse -> movieResponse.toMovie() },
        this.total_pages,
        this.total_results
    )
}
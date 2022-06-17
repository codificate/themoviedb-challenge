package com.challenge.themoviedb.data.network.response

import com.challenge.themoviedb.domain.model.MovieDetail

data class MovieDetailResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: BelongsToCollection,
    val budget: Int,
    val genres: List<GenreResponse>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetailResponse.toMovieDetail() : MovieDetail{
    return MovieDetail(
        this.backdrop_path,
        this.budget,
        this.genres.map { it.toGenre() },
        this.homepage,
        this.id,
        this.original_language,
        this.original_title,
        this.overview,
        this.poster_path,
        this.production_companies,
        this.release_date,
        this.title,
        this.vote_average.toString()
    )
}
package com.challenge.themoviedb.domain.model

import android.os.Parcelable
import com.challenge.themoviedb.data.network.response.MovieResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val rating: Double
): Parcelable

fun MovieResponse.toMovie(): Movie {
    return Movie(
        this.id,
        this.title,
        this.overview,
        this.poster_path,
        this.vote_average
    )
}
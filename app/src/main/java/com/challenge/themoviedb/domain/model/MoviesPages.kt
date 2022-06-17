package com.challenge.themoviedb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesPages(
    val page: Int,
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
): Parcelable
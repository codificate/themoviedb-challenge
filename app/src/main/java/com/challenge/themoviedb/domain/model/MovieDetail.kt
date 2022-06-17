package com.challenge.themoviedb.domain.model

import android.os.Parcelable
import com.challenge.themoviedb.data.network.response.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val backdrop_path: String,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val release_date: String,
    val title: String,
    val vote_average: String
): Parcelable
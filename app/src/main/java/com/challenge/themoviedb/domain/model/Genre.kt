package com.challenge.themoviedb.domain.model

import android.os.Parcelable
import com.challenge.themoviedb.data.network.response.GenreResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String
): Parcelable

fun GenreResponse.toGenre() : Genre {
    return Genre(this.id, this.name)
}
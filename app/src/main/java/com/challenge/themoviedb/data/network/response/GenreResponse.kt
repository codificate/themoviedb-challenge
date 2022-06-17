package com.challenge.themoviedb.data.network.response

import com.challenge.themoviedb.domain.model.Genre

data class GenreResponse(
    val id: Int,
    val name: String
)

fun GenreResponse.toGenre() : Genre {
    return Genre(this.id, this.name)
}
package com.challenge.themoviedb.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCompany(
    val id: Int,
    @SerializedName("logo_path") val logo_path: String,
    val name: String,
    val origin_country: String
): Parcelable
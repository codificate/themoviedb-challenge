package com.challenge.themoviedb.data.api

import com.challenge.themoviedb.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {

        val BUILDER: Retrofit.Builder by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
        }

    }

}
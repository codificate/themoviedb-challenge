package com.challenge.themoviedb.di

import com.challenge.themoviedb.data.api.RetrofitInstance
import com.challenge.themoviedb.data.api.AuthenticationApiService
import com.challenge.themoviedb.data.api.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TMBDModule {

    @Provides
    @Singleton
    fun provideTMBDApi() = RetrofitInstance.BUILDER

    @Provides
    @Singleton
    fun provideAuthenticationApiService(retrofitInstance: Retrofit.Builder) : AuthenticationApiService {
        return retrofitInstance.build()
            .create(AuthenticationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesApiService(retrofitInstance: Retrofit.Builder) : MoviesApiService {
        return retrofitInstance.build()
            .create(MoviesApiService::class.java)
    }

}
package com.challenge.themoviedb.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.challenge.themoviedb.data.api.AuthenticationApiService
import com.challenge.themoviedb.data.api.MoviesApiService
import com.challenge.themoviedb.data.api.RetrofitInstance
import com.challenge.themoviedb.data.repository.SessionManagerImpl
import com.challenge.themoviedb.domain.repository.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TMBDModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            "SESSION_MANAGER_PREFERENCES",
            MasterKey.Builder(context, "SESSION_MANAGER_PREFERENCES").build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Singleton
    @Provides
    fun provideSessionManager(preferences: SharedPreferences) : SessionManager {
        return SessionManagerImpl(preferences)
    }

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
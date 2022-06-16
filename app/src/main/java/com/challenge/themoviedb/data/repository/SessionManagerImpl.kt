package com.challenge.themoviedb.data.repository

import android.content.SharedPreferences
import com.challenge.themoviedb.domain.repository.SessionManager
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(private val preferences: SharedPreferences) : SessionManager {

    override fun setExpireAt(expireAt: String) {
        preferences.edit().apply {
            putString(EXPIRE_AT_KEY, expireAt)
        }.apply()
    }

    override fun getExpireAt(): String? {
        return preferences.getString(EXPIRE_AT_KEY, "")
    }

    override fun setToken(token: String) {
        preferences.edit().apply {
            putString(REQUESTED_TOKEN_KEY, token)
        }.apply()
    }

    override fun getToken(): String? {
        return preferences.getString(REQUESTED_TOKEN_KEY, "")
    }

    override fun setSessionId(sessionId: String) {
        preferences.edit().apply {
            putString(SESSION_ID_KEY, sessionId)
        }.apply()
    }

    override fun getSessionId(): String? {
        return preferences.getString(REQUESTED_TOKEN_KEY, "")
    }

    private companion object {
        const val EXPIRE_AT_KEY = "EXPIRE_AT_KEY"
        const val REQUESTED_TOKEN_KEY = "REQUESTED_TOKEN_KEY"
        const val SESSION_ID_KEY = "SESSION_ID_KEY"
    }
}
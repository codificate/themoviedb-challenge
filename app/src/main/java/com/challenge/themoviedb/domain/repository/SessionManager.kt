package com.challenge.themoviedb.domain.repository

interface SessionManager {

    fun setExpireAt(expireAt: String)
    fun getExpireAt(): String?
    fun setToken(token: String)
    fun getToken(): String?
    fun setSessionId(sessionId: String)
    fun getSessionId(): String?

}
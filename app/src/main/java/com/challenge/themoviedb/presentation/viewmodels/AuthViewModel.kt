package com.challenge.themoviedb.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.themoviedb.data.DataResource
import com.challenge.themoviedb.domain.repository.SessionManager
import com.challenge.themoviedb.domain.use_cases.auth.RequestTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val requestTokenUseCase: RequestTokenUseCase) : ViewModel() {

    private lateinit var sessionManager: SessionManager

    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

    fun requestNewToken() {
        requestTokenUseCase()
            .map { dataResourceResponse ->
                if (dataResourceResponse is DataResource.Success) {
                    dataResourceResponse.data?.let { authenticationResponse ->
                        sessionManager.setExpireAt(authenticationResponse.expires_at)
                        sessionManager.setToken(authenticationResponse.request_token)
                    }
                }
            }
            .conflate()
            .launchIn(viewModelScope)
    }

}
package com.example.tenderosapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED  ,
        INVALID_AUTHENTICATION// The user has authenticated successfully
    }
    val authenticationState = MutableLiveData<AuthenticationState>()

    //Intance
    init {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }
}

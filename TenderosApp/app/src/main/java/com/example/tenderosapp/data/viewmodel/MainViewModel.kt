package com.example.tenderosapp.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tenderosapp.data.Repository.PagofonRepository

class MainViewModel : ViewModel() {

    private var pagofonRepository : PagofonRepository

    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED  ,
        INVALID_AUTHENTICATION// The user has authenticated successfully
    }
    val authenticationState = MutableLiveData<AuthenticationState>()

    //Intance
    init {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
        pagofonRepository = PagofonRepository.getInstance()

    }
}

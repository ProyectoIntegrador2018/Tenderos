package com.example.tenderosapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tenderosapp.data.repository.AppRepository
import com.example.tenderosapp.data.repository.PagofonRepository
import com.example.tenderosapp.model.response.BalanceResponse

class MainViewModel : ViewModel() {

    private var pagofonRepository : PagofonRepository
    private var appRepository : AppRepository

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
        appRepository = AppRepository.getInstance()
    }

    fun getBalance(): LiveData<BalanceResponse?> = pagofonRepository.getBalance()

    fun queryGetBalance() = pagofonRepository.queryGetBalance()


    fun getIsEmailRegistered(): LiveData<Boolean?> = appRepository.getIsEmailRegistered()

    fun queryIsEmailRegistered(email : String) = appRepository.queryIsEmailRegistered(email)



}

package com.example.tenderosapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tenderosapp.data.repository.AppRepository
import com.example.tenderosapp.data.repository.PagofonRepository
import com.example.tenderosapp.model.Promo
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
    fun getIsEmailRegistered(): LiveData<Boolean?> = appRepository.getIsEmailRegistered()
    fun getRegisterPromoSucccess(): LiveData<Boolean?> = appRepository.getRegisterPromoSucccess()

    fun queryGetBalance() = pagofonRepository.queryGetBalance()

    fun queryIsEmailRegistered(email : String) = appRepository.queryIsEmailRegistered(email)

    fun queryRegisterPromo(promo : Promo, uid: String)  = appRepository.queryRegisterPromo(promo, uid)

    fun getPromotionList() : LiveData<ArrayList<Promo>?> = appRepository.getPromotionList()

    fun queryGetPromorionList(uid : String)  = appRepository.queryGetPromorionList(uid)
}

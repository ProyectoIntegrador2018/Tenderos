package com.example.tenderosapp.data.repository

import androidx.lifecycle.LiveData
import com.example.tenderosapp.data.client.AppClient
import com.example.tenderosapp.model.Promo

//Singleton
class AppRepository {
    private val appClient : AppClient

    init {
        appClient = AppClient.getInstance()
    }

    companion object {
        private var instance: AppRepository? = null
        fun getInstance(): AppRepository {
            if (instance == null) {
                instance = AppRepository()
            }
            return instance as AppRepository
        }
    }

    fun getIsEmailRegistered(): LiveData<Boolean?> = appClient.getIsEmailRegistered()

    fun queryIsEmailRegistered(email : String) = appClient.queryIsEmailRegistered(email)

    fun queryRegisterPromo(promo : Promo, uid : String )  = appClient.queryRegisterPromo(promo, uid)
    fun getRegisterPromoSucccess(): LiveData<Boolean?> = appClient.getRegisterPromoSucccess()


}
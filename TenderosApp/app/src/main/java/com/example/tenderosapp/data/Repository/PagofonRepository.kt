package com.example.tenderosapp.data.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tenderosapp.data.client.PagoFonClient
import com.example.tenderosapp.model.response.BalanceResponse
import com.example.tenderosapp.model.response.Encoded64Response

//Singleton
class PagofonRepository {
    private val pagofonClient : PagoFonClient
    var encoded64Response: MutableLiveData<Encoded64Response?>

    init {
        pagofonClient = PagoFonClient.getInstance()
        encoded64Response = MutableLiveData()
    }

    companion object {
        private var instance: PagofonRepository? = null
        fun getInstance(): PagofonRepository {
            if (instance == null) {
                instance = PagofonRepository()
            }
            return instance as PagofonRepository
        }
    }

    fun getBalance(): LiveData<BalanceResponse?> = pagofonClient.getBalance()

    fun queryGetBalance() = pagofonClient.queryGetBalance()


}
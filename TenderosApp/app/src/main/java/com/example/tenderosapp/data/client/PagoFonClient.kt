package com.example.tenderosapp.data.client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tenderosapp.BuildConfig
import com.example.tenderosapp.model.response.Encoded64Response
import com.example.tenderosapp.model.response.BalanceResponse
import com.example.tenderosapp.network.ApiServiceGenerator
import com.example.tenderosapp.network.endpoints.PagofonApi
import com.example.tenderosapp.util.Encrypter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class PagoFonClient {
    var pagofonApi: PagofonApi
    var balanceResponse: MutableLiveData<BalanceResponse?>
    val gson = Gson()

    init {
        balanceResponse = MutableLiveData()
        pagofonApi = ApiServiceGenerator.pagoPhonEndPoint
    }

    companion object {
        private var instance: PagoFonClient? = null
        fun getInstance(): PagoFonClient {
            if (instance == null) {
                instance = PagoFonClient()
            }
            return instance as PagoFonClient
        }
    }

    fun getBalance(): LiveData<BalanceResponse?> = balanceResponse

    fun queryGetBalance()= GlobalScope.launch(Dispatchers.Main) {
        val dataEncoded : String = Encrypter.encodeData(
            activationCode = BuildConfig.ACTIVATION_CODE,
            requestIP = BuildConfig.REQUEST_IP,
            requestUniqueID = BuildConfig.REQUEST_UNIQUE_ID,
            methodName = "GetBalance")
        
        val job: Job = launch {
            val encoded64ResponseResponse: Response<Encoded64Response> = retieveBalance(dataEncoded, BuildConfig.ACTIVATION_CODE)
            if(encoded64ResponseResponse.isSuccessful){
                val decryptedObject = Encrypter.decyptData(encoded64ResponseResponse.body()!!.data!!)
                val balanceDecypted:BalanceResponse = gson.fromJson(decryptedObject, BalanceResponse::class.java)
                balanceResponse.postValue(balanceDecypted)
            }
        }
        job.join()
    }

    suspend fun retieveBalance(data:String, activationCode:String): Response<Encoded64Response>
            = pagofonApi.getBalance(data = data, activationCode = activationCode)
}

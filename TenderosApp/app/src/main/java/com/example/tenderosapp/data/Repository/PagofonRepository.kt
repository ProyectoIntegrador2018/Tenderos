package com.example.tenderosapp.data.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tenderosapp.model.response.GetBalanceResponse
import com.example.tenderosapp.network.ApiServiceGenerator
import com.example.tenderosapp.network.endpoints.PagofonApi
import kotlinx.coroutines.*
import retrofit2.Response
import java.util.concurrent.TimeoutException

//Singleton
class PagofonRepository private constructor() {
    var pagofonApi: PagofonApi
    private val pagofonRepository: PagofonRepository
    var getBalanceResponse: MutableLiveData<GetBalanceResponse?>

    init {
        pagofonRepository = getInstance()
        getBalanceResponse = MutableLiveData()
        pagofonApi = ApiServiceGenerator.pagoPhonEndPoint
    }

    companion object {
        private var instance: PagofonRepository? = null
        fun getInstance(): PagofonRepository {
            if (instance == null) {
                instance =
                    PagofonRepository()
            }
            return instance as PagofonRepository
        }
    }

    fun getBalance(): LiveData<GetBalanceResponse?> = getBalanceResponse

    fun queryGetBalance(data:String, activationCode:String)
        = GlobalScope.launch(Dispatchers.Main) {
            val job: Job = launch {
                val getBalanceResponseResponse: Response<GetBalanceResponse>
                        = retieveBalance(data,activationCode)
                if(getBalanceResponseResponse.isSuccessful){
                    getBalanceResponse.postValue(getBalanceResponseResponse.body())
                }
            }
            job.join()
    }



    suspend fun retieveBalance(data:String, activationCode:String): Response<GetBalanceResponse>
            = pagofonApi.getBalance(
        data = data,
        activationCode = activationCode
    )

}
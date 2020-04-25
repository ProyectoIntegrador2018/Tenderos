package com.example.tenderosapp.network.endpoints

import com.example.tenderosapp.model.response.GetBalanceResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PagofonApi {
    @POST("oauth2/token")
    @FormUrlEncoded
    suspend fun getBalance(
        @Field("ActivationCode")  activationCode: String,
        @Field("Data")  data: String
    )  : Response<GetBalanceResponse>

}

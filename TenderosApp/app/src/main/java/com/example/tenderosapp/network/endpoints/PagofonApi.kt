package com.example.tenderosapp.network.endpoints

import com.example.tenderosapp.model.response.Encoded64Response
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PagofonApi {
    @POST("securerest/")
    @FormUrlEncoded
    suspend fun getBalance(
        @Field("ActivationCode")  activationCode: String,
        @Field("Data")  data: String
    )  : Response<Encoded64Response>

}

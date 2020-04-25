package com.example.tenderosapp.network.endpoints

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PagofonApi {
    @POST("oauth2/token")
    @FormUrlEncoded
    suspend fun getToken(
        @Field("grant_type")  userNameVaclient_idlue: String
    )  : Response<String>

}

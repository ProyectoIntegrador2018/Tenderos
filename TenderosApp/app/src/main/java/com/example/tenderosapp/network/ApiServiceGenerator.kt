package com.example.tenderosapp.network

import com.example.tenderosapp.BuildConfig
import com.example.tenderosapp.network.endpoints.PagofonApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiServiceGenerator {
    val interceptor = HttpLoggingInterceptor()
    val client: OkHttpClient.Builder  =  OkHttpClient().newBuilder().apply {
        readTimeout(10, TimeUnit.SECONDS)
        writeTimeout(10, TimeUnit.SECONDS)
        connectTimeout(10, TimeUnit.SECONDS)
        addInterceptor(interceptor)
        addInterceptor { chain ->
            var request = chain.request()
            request = request.newBuilder()
                .build()
            val response = chain.proceed(request)
            response
        }
    }

    val pagoPhonEndPoint by lazy {
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        Retrofit.Builder()
            .baseUrl(BuildConfig.PAGOFON_BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PagofonApi::class.java)
    }

}

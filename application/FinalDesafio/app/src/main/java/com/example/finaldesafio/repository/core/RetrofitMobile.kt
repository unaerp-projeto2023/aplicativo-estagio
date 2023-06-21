package com.example.finaldesafio.repository.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.example.finaldesafio.repository.api.ApiService

class RetrofitMobile {
    val apiService: ApiService
        get() = invoke().create(ApiService::class.java)

    private operator fun invoke(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(Constants.API_URL)
            .client(okHttp3())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun okHttp3() : OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(MicroServiceInterceptor())
            .build()
}
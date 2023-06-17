package com.example.finaldesafio.repository.core

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MicroServiceInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val requestBuilder = request.newBuilder()
            .header("Cache-Control", "no-cache")
            .header("Content-Type" , "application/json; charset=UTF-8")

        request = requestBuilder.build()
        return chain.proceed(request)
    }
}

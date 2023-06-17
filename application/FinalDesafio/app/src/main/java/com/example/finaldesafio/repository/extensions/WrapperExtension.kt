package com.example.finaldesafio.repository.extensions

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.finaldesafio.repository.model.WsResult

fun <T> Call<WsResult<T>>.serverWrapper(callback: (WsResult<T>) -> Unit) {
    this.apply {
        enqueue(object  : Callback<WsResult<T>> {
            override fun onResponse(call: Call<WsResult<T>>, response: Response<WsResult<T>>) {
                if (response.code() == 200) {
                    response.body()?.let { callback.invoke(it) }
                }
                else {
                    callback.invoke(WsResult<T>().also {
                        it.codeError = 9
                        it.message   = "Erro no sistema. Entre em contato com o administrador"
                    })
                }
            }
            override fun onFailure(call: Call<WsResult<T>>, t: Throwable) {
                callback.invoke(WsResult<T>().also {
                    it.codeError = 9
                    it.message   = "Erro no sistema. Entre em contato com o administrador"
                })
            }
        })
    }
}
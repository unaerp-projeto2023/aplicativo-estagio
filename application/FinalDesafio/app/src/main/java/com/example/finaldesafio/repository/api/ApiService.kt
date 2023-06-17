package com.example.finaldesafio.repository.api

import retrofit2.Call
import retrofit2.http.POST

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.WsResult

interface ApiService {
    @FormUrlEncoded
    @POST("/addUser.php")
    fun addUser(@Field("id") id: Int?,
                @Field("name") name: String?,
                @Field("password") password: String?,
                @Field("email") email: String?,
                @Field("type") type: String?) : Call<WsResult<User>>

    @FormUrlEncoded
    @POST("/login.php")
    fun getLogin(@Field("password") password: String?,
                 @Field("email") email: String?) : Call<WsResult<User>>
}
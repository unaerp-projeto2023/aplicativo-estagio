package com.example.finaldesafio.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * type = (1) usuario
 *        (2) empresa
 */
data class User(
    @SerializedName("id")       @Expose var userId: Long = 0,
    @SerializedName("name")     @Expose var userName: String? = null,
    @SerializedName("password") @Expose var userPassword: String? = null,
    @SerializedName("email")    @Expose var userEmail: String? = null,
    @SerializedName("type")     @Expose var userType: String? = null
) : Serializable
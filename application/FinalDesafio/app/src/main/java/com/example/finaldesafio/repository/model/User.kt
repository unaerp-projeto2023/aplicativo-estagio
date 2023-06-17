package com.example.finaldesafio.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * type = (1) usuario
 *        (2) empresa
 */
data class User(
    @SerializedName("id")       @Expose var id: Int? = 0,
    @SerializedName("name")     @Expose var name: String? = null,
    @SerializedName("password") @Expose var password: String? = null,
    @SerializedName("email")    @Expose var email: String? = null,
    @SerializedName("type")     @Expose var type: String? = null
) : Serializable
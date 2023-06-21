package com.example.finaldesafio.repository.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WsResult<T> (
    @SerializedName("result")
    var result: T? = null,
    @SerializedName("codeError")
    var codeError: Int? = null,
    @SerializedName("message")
    var message: String? = null
) : Serializable

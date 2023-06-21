package com.example.finaldesafio.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AnnouncementList(
    @SerializedName("items") @Expose var items : MutableList<Announcement>? = null
) : Serializable

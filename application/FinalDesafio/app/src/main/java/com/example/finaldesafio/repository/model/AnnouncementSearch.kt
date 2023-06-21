package com.example.finaldesafio.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AnnouncementSearch(
    @SerializedName("companyId")        @Expose var companyId : Long = 0,
    @SerializedName("userId")           @Expose var userId : Long = 0,
    @SerializedName("description")      @Expose var description : String? = null,
    @SerializedName("area_description") @Expose var areaDescription : String? = null,
    @SerializedName("locality")         @Expose var locality : String? = null,
    @SerializedName("myJobs")           @Expose var myJobs : Int = 0
) : Serializable

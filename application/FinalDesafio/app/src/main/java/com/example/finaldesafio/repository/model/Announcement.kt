package com.example.finaldesafio.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Announcement(
    @SerializedName("id")               @Expose var announcementId : Long = 0,
    @SerializedName("id_user")          @Expose var announcementUserId : Long = 0,
    @SerializedName("description")      @Expose var announcementDescription : String? = null,
    @SerializedName("contact_name")     @Expose var announcementContactName : String? = null,
    @SerializedName("contact_email")    @Expose var announcementContactEmail : String? = null,
    @SerializedName("contact_phone")    @Expose var announcementContactPhone : String? = null,
    @SerializedName("company")          @Expose var announcementCompany : String? = null,
    @SerializedName("company_show")     @Expose var announcementCompanyShow : String? = null,
    @SerializedName("area_description") @Expose var announcementAreaDescription : String? = null,
    @SerializedName("locality")         @Expose var announcementLocality : String? = null,
    @SerializedName("start_date")       @Expose var announcementStartDate : String? = null,
    @SerializedName("final_date")       @Expose var announcementFinalDate : String? = null,
    @SerializedName("remuneration")     @Expose var announcementRemuneration : Double = 0.00,
    @SerializedName("total")            @Expose var announcementTotal : Long = 0,
    @SerializedName("myJobs")           @Expose var myJobs : Int = 0
) : Serializable

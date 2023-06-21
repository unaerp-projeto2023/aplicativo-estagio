package com.example.finaldesafio.repository.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.WsResult
import com.example.finaldesafio.repository.model.AnnouncementList

interface ApiService {
    @FormUrlEncoded
    @POST("/addUser.php")
    fun addUser(@Field("id") id: Long?,
                @Field("name") name: String?,
                @Field("password") password: String?,
                @Field("email") email: String?,
                @Field("type") type: String?) : Call<WsResult<User>>

    @FormUrlEncoded
    @POST("/editUser.php")
    fun editUser(@Field("id") id: Long?,
                @Field("name") name: String?,
                @Field("email") email: String?) : Call<WsResult<User>>

    @FormUrlEncoded
    @POST("/addAnnouncement.php")
    fun addAnnouncement(@Field("id") id: Long?,
                        @Field("id_user") userId: Long?,
                        @Field("description") description: String?,
                        @Field("contact_name") contactName: String?,
                        @Field("contact_email") contactEmail: String?,
                        @Field("contact_phone")  contactPhone: String?,
                        @Field("company_show") companyShow: String?,
                        @Field("area_description") areaDescription: String?,
                        @Field("locality")locality: String?,
                        @Field("start_date") startDate: String?,
                        @Field("final_date") finalDate: String?,
                        @Field("remuneration") remuneration: String?) : Call<WsResult<Nothing>>

    @FormUrlEncoded
    @POST("/announcementList.php")
    fun announcementList(@Field("companyId") companyId: Long?,
                         @Field("userId") userId: Long?,
                         @Field("description") description: String?,
                         @Field("area_description") areaDescription: String?,
                         @Field("locality") locality: String?,
                         @Field("myJobs") myJobs: Int?) : Call<WsResult<AnnouncementList>>

    @GET("/selection.php")
    fun selection(@Query("id_announcement") id_announcement: Long?,
                  @Query("id_user") id_user: Long?) : Call<WsResult<Nothing>>

    @FormUrlEncoded
    @POST("/login.php")
    fun getLogin(@Field("email") email: String?,
                 @Field("password") password: String?) : Call<WsResult<User>>
}
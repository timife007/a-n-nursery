package com.timife.a_n_nursery_app.settings.profile.network

import com.timife.a_n_nursery_app.settings.profile.response.ProfileItem
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PUT


interface ProfileApi {
    @GET("/profile/")
    suspend fun getProfile(): ProfileItem


    @FormUrlEncoded
    @PUT("/profile/update/")
    suspend fun updateProfile(
        @Field("user") userName: UserName,
        @Field("phone_number") phoneNumber: String
    ): ProfileItem
}

data class UserName(
    val first_name: String,
    val last_name: String
)

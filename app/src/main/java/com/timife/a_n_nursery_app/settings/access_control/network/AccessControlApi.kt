package com.timife.a_n_nursery_app.settings.access_control.network

import com.timife.a_n_nursery_app.settings.access_control.response.InvitedUsers
import com.timife.a_n_nursery_app.settings.access_control.response.Invitee
import retrofit2.http.*

interface AccessControlApi {

    @GET("/inviter/")
    suspend fun getInvitees(
        @Query("page")
        pageNumber: Int?
    ): InvitedUsers

    @FormUrlEncoded
    @POST("/inviter/")
    suspend fun inviteUser(
        @Field("email") inviteeEmail : String,
        @Field("name") inviteeName:String
    ):Invitee
}
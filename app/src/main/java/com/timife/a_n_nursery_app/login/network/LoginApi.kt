package com.timife.a_n_nursery_app.login.network

import com.timife.a_n_nursery_app.login.response.LoginResponse
import retrofit2.http.*

interface LoginApi {
    @FormUrlEncoded
    @POST("/api/token/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}
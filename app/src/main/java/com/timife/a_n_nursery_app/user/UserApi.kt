package com.timife.a_n_nursery_app.user

import okhttp3.ResponseBody
import retrofit2.http.POST

interface UserApi {
    @POST("/auth/token/logout/")
    suspend fun logout(): ResponseBody
}
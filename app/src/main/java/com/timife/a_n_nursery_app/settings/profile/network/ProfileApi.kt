package com.timife.a_n_nursery_app.settings.profile.network

import com.timife.a_n_nursery_app.settings.profile.response.ProfileResponse
import retrofit2.http.GET

interface ProfileApi {
    @GET("/profiles/")
    suspend fun getProfile(): ProfileResponse
}
package com.timife.a_n_nursery_app.settings.profile.network

import com.timife.a_n_nursery_app.settings.profile.response.Profile
import retrofit2.http.GET




interface ProfileApi {
    @GET("/auth/users/me/")
    suspend fun getProfile(): Profile
}

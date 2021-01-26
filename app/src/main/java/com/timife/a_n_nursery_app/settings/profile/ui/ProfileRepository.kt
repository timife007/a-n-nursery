package com.timife.a_n_nursery_app.settings.profile.ui

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.settings.profile.network.ProfileApi

class ProfileRepository(
    private val api: ProfileApi
) : BaseRepository() {

    suspend fun getProfile() = safeApiCall {
        api.getProfile()
    }
}
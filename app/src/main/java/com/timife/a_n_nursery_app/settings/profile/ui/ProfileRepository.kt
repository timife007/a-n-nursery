package com.timife.a_n_nursery_app.settings.profile.ui

import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.settings.profile.network.ProfileApi
import com.timife.a_n_nursery_app.settings.profile.network.UserName
import com.timife.a_n_nursery_app.settings.profile.response.ProfileItem

class ProfileRepository(
    private val api: ProfileApi
) : BaseRepository() {

    suspend fun getProfile() = safeApiCall {
        api.getProfile()
    }

    suspend fun updateProfile(
        firstName: String,
        lastName:String,
        phoneNumber: String
    ): Resource<ProfileItem> {
        return safeApiCall {
            api.updateProfile(firstName,lastName,phoneNumber)
        }
    }
}
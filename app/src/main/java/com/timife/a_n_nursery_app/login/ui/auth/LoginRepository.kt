package com.timife.a_n_nursery_app.login.ui.auth

import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.UserPreferences
import com.timife.a_n_nursery_app.login.network.LoginApi
import com.timife.a_n_nursery_app.login.response.LoginResponse
import com.timife.a_n_nursery_app.base.BaseRepository

class LoginRepository(
    private val api: LoginApi,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun login(
        email: String,
        password: String
    ): Resource<LoginResponse> {
        return safeApiCall {
            api.login(email, password)
        }
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
}
package com.timife.a_n_nursery_app.login.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.login.ui.base.BaseRepository
import com.timife.a_n_nursery_app.settings.profile.ui.ProfileRepository
import com.timife.a_n_nursery_app.settings.profile.ui.ProfileViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(repository as LoginRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(repository as ProfileRepository) as T
            else -> throw  IllegalArgumentException("ViewModelClass not found")
        }
    }
}
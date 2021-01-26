package com.timife.a_n_nursery_app.settings.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.settings.profile.response.Profile
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : BaseViewModel(profileRepository) {
    private val _profile:MutableLiveData<Resource<Profile>> = MutableLiveData()
    val profile:LiveData<Resource<Profile>>
    get() = _profile

    fun getUserProfile() =viewModelScope.launch {
        _profile.value= Resource.Loading
        _profile.value = profileRepository.getProfile()
    }
}
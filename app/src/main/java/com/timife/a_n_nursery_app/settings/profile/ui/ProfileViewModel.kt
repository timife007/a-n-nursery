package com.timife.a_n_nursery_app.settings.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.login.Resource
import com.timife.a_n_nursery_app.settings.profile.response.ProfileResponse
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _profile:MutableLiveData<Resource<ProfileResponse>> = MutableLiveData()
    val profile:LiveData<Resource<ProfileResponse>>
    get() = _profile

    fun getUserProfile() =viewModelScope.launch {
        _profile.value= Resource.Loading
        _profile.value = profileRepository.getProfile()
    }
}
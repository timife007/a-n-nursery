package com.timife.a_n_nursery_app.settings.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.settings.profile.network.UserName
import com.timife.a_n_nursery_app.settings.profile.response.ProfileItem
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : BaseViewModel(profileRepository) {
    private val _profile:MutableLiveData<Resource<ProfileItem>> = MutableLiveData()
    val profile:LiveData<Resource<ProfileItem>>
    get() = _profile

    private val _update: MutableLiveData<Resource<ProfileItem>> = MutableLiveData()
    val update:LiveData<Resource<ProfileItem>>
    get() = _update

    fun getUserProfile() =viewModelScope.launch {
        _profile.value= Resource.Loading
        _profile.value = profileRepository.getProfile()
    }

    fun updateUserProfile(firstName: String,lastName:String,phoneNumber: String)= viewModelScope.launch {
        _update.value = Resource.Loading
        _update.value = profileRepository.updateProfile(firstName,lastName,phoneNumber)
    }
}
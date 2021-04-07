package com.timife.a_n_nursery_app.settings.access_control

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timife.a_n_nursery_app.Resource
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.settings.access_control.response.InvitedUsers
import com.timife.a_n_nursery_app.settings.access_control.response.Invitee
import kotlinx.coroutines.launch

class AccessControlViewModel(
    private val accessControlRepository: AccessControlRepository
) : BaseViewModel(accessControlRepository) {


//    private val _invitedUser: MutableLiveData<Resource<InvitedUsers>> = MutableLiveData()
//    val invitedUser: LiveData<Resource<InvitedUsers>>
//        get() = _invitedUser

    fun getInvitedUser() = accessControlRepository.getInvitedUsers().cachedIn(viewModelScope)
}
package com.timife.a_n_nursery_app.settings.access_control

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.data.FilterPagingSource
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.settings.access_control.network.AccessControlApi
import com.timife.a_n_nursery_app.settings.access_control.network.InviteePagingSource

class AccessControlRepository(
    private val accessControlApi: AccessControlApi
) : BaseRepository() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 14
    }

    suspend fun inviteUser(userEmail:String,userName: String) = safeApiCall {
        accessControlApi.inviteUser(userEmail,userName)
    }

    fun getInvitedUsers() =
        Pager(
            config = PagingConfig(
                pageSize = AccessControlRepository.NETWORK_PAGE_SIZE,
                maxSize = 350,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                InviteePagingSource(accessControlApi)
            }
        ).liveData


}
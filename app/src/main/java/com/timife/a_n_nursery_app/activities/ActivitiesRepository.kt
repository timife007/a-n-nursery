package com.timife.a_n_nursery_app.activities

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.timife.a_n_nursery_app.activities.network.ActivitiesApi
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.data.InventoryPagingSource
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository

class ActivitiesRepository(private val api: ActivitiesApi): BaseRepository() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 14
    }

    fun getActivities() =
        Pager(
            config = PagingConfig(
                pageSize = ActivitiesRepository.NETWORK_PAGE_SIZE,
                maxSize = 350,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ActivitiesPagingSource(api)
            }
        ).liveData
}
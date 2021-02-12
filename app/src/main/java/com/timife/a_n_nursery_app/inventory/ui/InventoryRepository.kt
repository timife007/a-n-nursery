package com.timife.a_n_nursery_app.inventory.ui

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.data.FilterPagingSource
import com.timife.a_n_nursery_app.inventory.data.InventoryPagingSource
import com.timife.a_n_nursery_app.inventory.network.InventoryApi

class InventoryRepository(private val api: InventoryApi) : BaseRepository() {
    companion object {
        private const val NETWORK_PAGE_SIZE = 14
    }

    suspend fun postInventoryProducts(
        productName: String,
        botanicalName: String,
        size: String,
        classification: String,
        color: String,
        price: String,
        cost: String,
        lot: String,
        location: String,
        quantity: String,
        category: String
    ) = safeApiCall {
        api.saveInventoryItem(
            productName,
            botanicalName,
            size,
            classification,
            color,
            price,
            cost,
            lot,
            location,
            quantity,
            category
        )
    }


    fun getFilterResults(category: String) =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = 350,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FilterPagingSource(api, category)
            }
        ).liveData

    fun getSearchResults(searchQuery: String) =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = 350,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                InventoryPagingSource(api, searchQuery)
            }
        ).liveData


}
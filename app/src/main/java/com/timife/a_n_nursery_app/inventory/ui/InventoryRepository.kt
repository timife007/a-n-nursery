package com.timife.a_n_nursery_app.inventory.ui

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.data.InventoryPagingSource
import com.timife.a_n_nursery_app.inventory.network.InventoryApi

class InventoryRepository(private val api: InventoryApi) : BaseRepository() {
    suspend fun getInventoryProducts(pageNumber: Int) = safeApiCall {
        api.getInventory(pageNumber).results
    }
//    fun getSearchResult(query:String)=
//        Pager(
//            config = PagingConfig(pageSize = 15,
//            maxSize = 350,
//            enablePlaceholders = false),
//            pagingSourceFactory = {InventoryPagingSource(api,query)}
//        ).liveData

}
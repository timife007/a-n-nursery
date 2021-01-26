package com.timife.a_n_nursery_app.inventory.ui

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.inventory.network.InventoryApi

class InventoryRepository(private val api: InventoryApi): BaseRepository(){
    suspend fun getInventoryProducts() = safeApiCall {
        api.getInventory()
    }

}
package com.timife.a_n_nursery_app.inventory.network

import com.timife.a_n_nursery_app.inventory.response.InventoryProducts
import retrofit2.http.GET

interface InventoryApi {
    @GET("/products/")
    suspend fun getInventory(): InventoryProducts
}
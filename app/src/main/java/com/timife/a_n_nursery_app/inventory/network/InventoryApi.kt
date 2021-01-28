package com.timife.a_n_nursery_app.inventory.network

import com.timife.a_n_nursery_app.inventory.response.InventoryItems
import retrofit2.http.GET
import retrofit2.http.Query

interface InventoryApi {
    @GET("/products/")
    suspend fun getInventory(
        @Query("page")
        pageNumber: Int = 1
    ): InventoryItems

    @GET("/products/list/")
    suspend fun getSearchInventory(
        @Query("search")
        searchQuery: String,
        @Query("page")
        pageNumber: Int =1
    ): InventoryItems
}
package com.timife.a_n_nursery_app.sales.network

import com.timife.a_n_nursery_app.inventory.data.InventoryItem
import retrofit2.http.GET
import retrofit2.http.Query

interface SalesApi {
    @GET("/product/barcode_search/")
    suspend fun getProductByBarcode(
        @Query("barcode_digit") barcode: String
    ): InventoryItem
}
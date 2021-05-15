package com.timife.a_n_nursery_app.sales.network

import com.timife.a_n_nursery_app.inventory.response.InventoryItems
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SalesApi {
    @GET("/product/barcode_search/")
    suspend fun getProductByBarcode(
        @Query("barcode_digit") barcode: String
    ): InventoryItems

    @POST("/sales/device-code/")
    suspend fun getTerminalCode(
    ):TerminalCode
}
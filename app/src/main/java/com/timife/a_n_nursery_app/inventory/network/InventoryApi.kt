package com.timife.a_n_nursery_app.inventory.network

import com.timife.a_n_nursery_app.inventory.response.InventoryItems
import com.timife.a_n_nursery_app.inventory.response.Result
import retrofit2.http.*

interface InventoryApi {

    //Search api interface
    @GET("/product/list/")
    suspend fun getSearchInventory(
        @Query("name")
        searchQuery: String?,
        @Query("page")
        pageNumber: Int?
    ): InventoryItems

    //Filter by category api interface
    @GET("/product/list/")
    suspend fun getFilterByCategoryInventory(
        @Query("category")
        filterQuery: String?,
        @Query("page")
        pageNumber: Int = 1
    ): InventoryItems



    @FormUrlEncoded
    @POST("/products/")
    suspend fun saveInventoryItem(
        @Field("name") productName: String,
        @Field("botanical_name") botanicalName: String,
        @Field("size") size: String,
        @Field("classification") classification: String,
        @Field("color") color: String,
        @Field("price") price: String,
        @Field("cost") cost: String,
        @Field("lot") lot: String,
        @Field("location") location: String,
        @Field("quantity") quantity: String,
        @Field("category") category: String
    ): Result
}
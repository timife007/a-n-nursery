package com.timife.a_n_nursery_app.inventory.network

import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems
import com.timife.a_n_nursery_app.inventory.classifications.network.ClassificationItems
import com.timife.a_n_nursery_app.inventory.locations.network.LocationItems
import com.timife.a_n_nursery_app.inventory.lots.network.LotItems
import com.timife.a_n_nursery_app.inventory.response.Inventory
import com.timife.a_n_nursery_app.inventory.response.InventoryItems
import retrofit2.http.*

interface InventoryApi {

    @GET("/categories/")
    suspend fun getCategories(
    ): CategoryItems

    @GET("/locations/")
    suspend fun getLocations(
    ): LocationItems

    @GET("/lots/")
    suspend fun getLots(
    ): LotItems

    @GET("/classifications/")
    suspend fun getClassification(
    ): ClassificationItems

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
        pageNumber: Int?
    ): InventoryItems

    @DELETE("/products/{id}/")
    suspend fun deleteInventoryItems(
        @Path("id")
        deleteId: Int?
    )


    @FormUrlEncoded
    @POST("/product/create/")
    suspend fun saveInventoryItem(
        @Field("name") productName: String,
        @Field("botanical_name") botanicalName: String,
        @Field("size") size: String,
        @Field("classification") classification: Int,
        @Field("color") color: String,
        @Field("price") price: String,
        @Field("cost") cost: String,
        @Field("lot") lot: Int,
        @Field("location") location: Int,
        @Field("quantity") quantity: Int,
        @Field("category") category: Int
    ): Inventory

    @FormUrlEncoded
    @PUT("/products/{id}/")
    suspend fun updateInventoryItem(
        @Path("id") productId: Int,
        @Field("name") productName: String,
        @Field("botanical_name") botanicalName: String,
        @Field("size") size: String,
        @Field("classification.name") classification: String,
        @Field("color") color: String,
        @Field("price") price: String,
        @Field("cost") cost: String,
        @Field("lot.name") lot: String,
        @Field("location.name") location: String,
        @Field("quantity") quantity: Int,
        @Field("category.name") category: String
    ): Inventory
}
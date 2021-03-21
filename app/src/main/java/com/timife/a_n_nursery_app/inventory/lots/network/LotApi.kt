package com.timife.a_n_nursery_app.inventory.lots.network

import com.timife.a_n_nursery_app.inventory.locations.network.Location
import retrofit2.http.*

interface LotApi {
    @GET("/lots/")
    suspend fun getLots(
        @Query("page")
        pageNumber:Int? = 1
    ): LotItems


    @FormUrlEncoded
    @POST("/lots/")
    suspend fun saveLot(
        @Field("name") lotName: String
    ): Lot

    @FormUrlEncoded
    @PUT("/lots/")
    suspend fun updateLot(
        @Field("name") lotName: String
    )
}
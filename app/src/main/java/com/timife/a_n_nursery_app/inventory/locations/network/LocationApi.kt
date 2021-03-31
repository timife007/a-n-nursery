package com.timife.a_n_nursery_app.inventory.locations.network

import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryItems
import retrofit2.http.*

interface LocationApi {

    @GET("/locations/")
    suspend fun getLocations(
        @Query("page")
        pageNumber: Int? =1
    ):LocationItems


    @FormUrlEncoded
    @POST("/locations/")
    suspend fun saveLocation(
        @Field("name") locationName: String
    ): Location

    @FormUrlEncoded
    @PUT("/locations/{id}/")
    suspend fun updateLocation(
        @Path("id") locationId: Int,
        @Field("name") locationName: String
    ): Location
}
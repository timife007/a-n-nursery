package com.timife.a_n_nursery_app.inventory.classifications.network

import com.timife.a_n_nursery_app.inventory.categories.network.Category
import retrofit2.http.*

interface ClassificationApi {

    @GET("/classifications/")
    suspend fun getClassifications(
        @Query("page")
        pageNumber:Int? = 1
    ):ClassificationItems

    @FormUrlEncoded
    @POST("/classifications/")
    suspend fun saveClassification(
        @Field("name") classificationName: String
    ): Classification

    @FormUrlEncoded
    @PUT("/classifications/{id}/")
    suspend fun updateClassification(
        @Path("id") classificationId: Int,
        @Field("name") classificationName: String
    ): Classification

    @DELETE("/classifications/{id}/")
    suspend fun deleteClassification(
        @Path("id") classificationId: Int
    )
}
package com.timife.a_n_nursery_app.inventory.categories.network

import retrofit2.http.*

interface CategoryApi {
    @GET("/categories/")
    suspend fun getCategories(
    ):CategoryItems

    @FormUrlEncoded
    @POST("/categories/")
    suspend fun saveCategories(
        @Field("name") categoryName: String
    ): Category

    @FormUrlEncoded
    @PUT("/categories/{id}/")
    suspend fun updateCategory(
        @Path("id") categoryId: Int,
        @Field("name") categoryName: String
    ): Category
}
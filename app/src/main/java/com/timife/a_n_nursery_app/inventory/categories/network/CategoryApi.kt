package com.timife.a_n_nursery_app.inventory.categories.network

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryApi {
    @GET("/categories/")
    suspend fun getCategories(
    ):CategoryItems

    @FormUrlEncoded
    @POST("/categories/")
    suspend fun saveCategories(
        @Field("name") categoryName: String
    ): Category
}
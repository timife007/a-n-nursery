package com.timife.a_n_nursery_app.vendor.network

import com.timife.a_n_nursery_app.vendor.response.VendorItem
import com.timife.a_n_nursery_app.vendor.response.Vendors
import retrofit2.http.*

interface VendorsApi {
    @GET("/vendors/")
    suspend fun getVendors(
        @Query("page")
        pageNumber: Int = 1
    ): Vendors

    @GET("/vendors/list/")
    suspend fun getSearchVendors(
        @Query("first_name")
        firstName: String?,
        @Query("page")
        pageNumber: Int = 1
    ):Vendors

    @FormUrlEncoded
    @POST("/vendors/")
    suspend fun postVendors(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("company") company: String,
        @Field("type") type: String,
        @Field("phone_number") phoneNumber: String
    ): VendorItem
}
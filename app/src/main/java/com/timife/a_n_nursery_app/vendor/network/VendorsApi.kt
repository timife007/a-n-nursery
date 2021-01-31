package com.timife.a_n_nursery_app.vendor.network

import com.timife.a_n_nursery_app.vendor.response.Vendors
import retrofit2.http.GET
import retrofit2.http.Query

interface VendorsApi {
    @GET("/vendors/")

    suspend fun getVendors(@Query("page")
                               pageNumber: Int = 1): Vendors
}
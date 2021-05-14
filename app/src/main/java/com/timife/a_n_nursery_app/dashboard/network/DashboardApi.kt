package com.timife.a_n_nursery_app.dashboard.network

import com.timife.a_n_nursery_app.dashboard.response.DashboardItems
import com.timife.a_n_nursery_app.dashboard.response.TransactionCategory
import com.timife.a_n_nursery_app.dashboard.response.TransactionPrice
import com.timife.a_n_nursery_app.dashboard.response.TransactionProduct
import retrofit2.Call
import retrofit2.http.GET

interface DashboardApi {
    @GET("/dashboard/")
    suspend fun getDashboard(
    ): DashboardItems

    @GET("/transaction/category/")
    suspend fun getDashboardCategoriesChart(
    ): TransactionCategory

    @GET("/transaction/price/")
    suspend fun getDashboardPriceChart(
    ): TransactionPrice

    @GET("/transaction/product/")
    suspend fun getDashboardProductChart(
    ): TransactionProduct

}
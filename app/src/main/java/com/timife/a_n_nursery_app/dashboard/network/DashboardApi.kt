package com.timife.a_n_nursery_app.dashboard.network

import com.timife.a_n_nursery_app.dashboard.response.DashboardItems
import retrofit2.http.GET

interface DashboardApi {
    @GET("/dashboard/")
    suspend fun getDashboard(
    ): DashboardItems
}
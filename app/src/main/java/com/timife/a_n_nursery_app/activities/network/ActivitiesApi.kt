package com.timife.a_n_nursery_app.activities.network

import com.timife.a_n_nursery_app.activities.response.Activities
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivitiesApi {
    @GET("/activities/")
    suspend fun getActivities(
        @Query("page")
        pageNumber: Int?
    ): Activities
}
package com.timife.a_n_nursery_app.dashboard

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.dashboard.network.DashboardApi
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.network.InventoryApi

class DashBoardRepository(private val api: DashboardApi) : BaseRepository()  {

    suspend fun getDashboard() = safeApiCall {
        api.getDashboard()
    }

    suspend fun getCategoriesChart() = safeApiCall {
        api.getDashboardCategoriesChart()
    }

    suspend fun getPriceChart() = safeApiCall {
        api.getDashboardPriceChart()
    }

    suspend fun getProductChart() = safeApiCall {
        api.getDashboardProductChart()
    }
}

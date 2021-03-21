package com.timife.a_n_nursery_app.dashboard.response

data class DashboardItems(
    val low_inventory: Int,
    val net_sales: String,
    val products_sold: Int,
    val transactions: Int
)
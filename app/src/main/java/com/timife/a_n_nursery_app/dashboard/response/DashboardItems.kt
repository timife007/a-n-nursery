package com.timife.a_n_nursery_app.dashboard.response

data class DashboardItems(
    val low_inventory: Int,
    val net_sales: String,
    val products_sold: Int,
    val transactions: Int
)


data class TransactionCategory(val Plants: Int, val Trees: Int, val Soil: Int)

data class TransactionDay(val lastweek: Int, val two_weeks: Int)

data class TransactionPrice(
    val monday: TransactionDay,
    val tuesday: TransactionDay,
    val wednesday: TransactionDay,
    val thursday: TransactionDay,
    val friday: TransactionDay
)

data class TransactionProduct(
    val monday: TransactionDay,
    val tuesday: TransactionDay,
    val wednesday: TransactionDay,
    val thursday: TransactionDay,
    val friday: TransactionDay
)


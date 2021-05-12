package com.timife.a_n_nursery_app.sales.network

data class DeviceCode(
    val code: String,
    val created_at: String,
    val id: String,
    val location_id: String,
    val name: String,
    val pair_by: String,
    val product_type: String,
    val status: String,
    val status_changed_at: String
)
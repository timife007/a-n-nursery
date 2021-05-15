package com.timife.a_n_nursery_app.activities.response

data class Target(
    val address: String,
    val created: String,
    val gender: String,
    val id: Int,
    val is_paid: Boolean,
    val order_items: List<OrderItem>,
    val phone_number: String,
    val picture: Any,
    val reference: String,
    val square_id: String,
    val total_cost: String,
    val updated: String,
    val user: User
)
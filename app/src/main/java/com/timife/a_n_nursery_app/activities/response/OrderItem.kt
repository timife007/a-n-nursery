package com.timife.a_n_nursery_app.activities.response

data class OrderItem(
    val cost: String,
    val id: Int,
    val price: String,
    val product: Product,
    val quantity: Int
)
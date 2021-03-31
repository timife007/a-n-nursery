package com.timife.a_n_nursery_app.inventory.response

data class InventoryItems(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)
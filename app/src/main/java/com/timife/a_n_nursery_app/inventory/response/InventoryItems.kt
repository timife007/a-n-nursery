package com.timife.a_n_nursery_app.inventory.response

data class InventoryItems(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Inventory>
)
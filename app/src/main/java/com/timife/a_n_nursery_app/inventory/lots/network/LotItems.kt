package com.timife.a_n_nursery_app.inventory.lots.network

data class LotItems(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Lot>
)
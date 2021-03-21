package com.timife.a_n_nursery_app.inventory.classifications.network

data class ClassificationItems(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Classification>
)
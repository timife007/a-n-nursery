package com.timife.a_n_nursery_app.inventory.categories.network

data class CategoryItems(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Category>
)
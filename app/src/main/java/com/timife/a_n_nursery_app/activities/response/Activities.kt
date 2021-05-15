package com.timife.a_n_nursery_app.activities.response

data class Activities(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Activity>
)
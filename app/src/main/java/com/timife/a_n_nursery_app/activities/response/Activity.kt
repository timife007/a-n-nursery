package com.timife.a_n_nursery_app.activities.response

data class Activity(
    val created: String,
    val id: Int,
    val target: Target,
    val user: UserX,
    val verb: String
)
package com.timife.a_n_nursery_app.settings.profile.response

data class User(
    val email: String,
    val first_name: String,
    val id: Int,
    val is_admin: Boolean,
    val last_name: String
)
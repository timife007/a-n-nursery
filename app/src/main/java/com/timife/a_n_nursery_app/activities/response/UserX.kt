package com.timife.a_n_nursery_app.activities.response

data class UserX(
    val email: String,
    val first_name: String,
    val id: Int,
    val is_active: Boolean,
    val last_name: String,
    val phone_number: String,
    val user_type: String
)
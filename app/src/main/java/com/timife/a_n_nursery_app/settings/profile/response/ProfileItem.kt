package com.timife.a_n_nursery_app.settings.profile.response

data class ProfileItem(
    val address: String,
    val created: String,
    val gender: String,
    val id: Int,
    val phone_number: String,
    val picture: Any,
    val updated: String,
    val user: User
)
package com.timife.a_n_nursery_app.settings.profile.response

data class ProfileResponseItem(
    val address: String?,
    val created: String?,
    val gender: String?,
    val phone_number: String?,
    val picture: Any?,
    val updated: String?,
    val user: User?
)
package com.timife.a_n_nursery_app.settings.access_control.response

data class InvitedUsers(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Invitee>
)
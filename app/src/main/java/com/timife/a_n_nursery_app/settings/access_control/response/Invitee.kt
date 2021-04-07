package com.timife.a_n_nursery_app.settings.access_control.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Invitee(
    val email: String?,
    val name: String?
):Parcelable
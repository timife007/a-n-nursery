package com.timife.a_n_nursery_app.inventory.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Classification(
    val id: Int?,
    val name: String?
):Parcelable
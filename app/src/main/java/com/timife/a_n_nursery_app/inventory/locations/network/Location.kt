package com.timife.a_n_nursery_app.inventory.locations.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val id: Int?,
    val name: String?
):Parcelable
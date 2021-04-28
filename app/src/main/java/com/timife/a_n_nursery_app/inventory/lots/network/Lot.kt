package com.timife.a_n_nursery_app.inventory.lots.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lot(
    val id: Int?,
    val name: String
):Parcelable
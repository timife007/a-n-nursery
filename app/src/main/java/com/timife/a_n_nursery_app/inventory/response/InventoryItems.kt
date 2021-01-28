package com.timife.a_n_nursery_app.inventory.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class InventoryItems(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
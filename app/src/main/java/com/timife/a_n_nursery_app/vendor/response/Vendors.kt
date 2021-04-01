package com.timife.a_n_nursery_app.vendor.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Vendors(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<VendorItem>
)
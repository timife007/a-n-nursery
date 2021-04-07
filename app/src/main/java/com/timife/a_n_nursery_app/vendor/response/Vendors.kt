package com.timife.a_n_nursery_app.vendor.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Vendors(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<VendorItem>
)
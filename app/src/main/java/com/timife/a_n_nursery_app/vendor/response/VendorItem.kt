package com.timife.a_n_nursery_app.vendor.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class VendorItem(
    val id: Int?,
    val first_name: String?,
    val last_name: String?,
    val email: String?,
    val company: String?,
    val type: String?,
    val phone_number: String?,
    val updated: String?,
    val created: String?
):Parcelable
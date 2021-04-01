package com.timife.a_n_nursery_app.vendor.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendorItem(
    val company: String?,
    val created: String?,
    val email: String?,
    val first_name: String?,
    val id: Int?,
    val last_name: String?,
    val phone_number: String?,
    val type: String?,
    val updated: String?,
    val user: Int?
):Parcelable
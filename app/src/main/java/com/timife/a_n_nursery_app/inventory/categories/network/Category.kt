package com.timife.a_n_nursery_app.inventory.categories.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: Int?,
    val name: String?
): Parcelable


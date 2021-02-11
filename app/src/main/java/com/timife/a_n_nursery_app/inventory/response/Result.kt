package com.timife.a_n_nursery_app.inventory.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    val botanical_name: String?,
    val category: String?,
    val classification: String?,
    val color: String?,
    val cost: String?,
    val created: String?,
    val id: Int?,
    val location: String?,
    val lot: String?,
    val name: String?,
    val price: String?,
    val quantity: String?,
    val size: String?,
    val updated: String?
) : Parcelable
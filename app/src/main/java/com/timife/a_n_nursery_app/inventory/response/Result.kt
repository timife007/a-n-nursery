package com.timife.a_n_nursery_app.inventory.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    val barcode_digit: String?,
    val barcode_url: String?,
    val botanical_name: String?,
    val category: Category?,
    val classification: Classification?,
    val color: String?,
    val cost: String?,
    val created: String?,
    val id: Int?,
    val image: String?,
    val location: Location?,
    val lot: Lot?,
    val name: String?,
    val price: String?,
    val quantity: Int?,
    val size: String?,
    val updated: String?
):Parcelable
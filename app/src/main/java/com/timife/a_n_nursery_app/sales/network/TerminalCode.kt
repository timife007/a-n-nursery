package com.timife.a_n_nursery_app.sales.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TerminalCode(
    val device_code: DeviceCode
):Parcelable
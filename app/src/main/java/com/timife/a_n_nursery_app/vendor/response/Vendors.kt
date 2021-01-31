package com.timife.a_n_nursery_app.vendor.response


data class Vendors(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<VendorItem>
)
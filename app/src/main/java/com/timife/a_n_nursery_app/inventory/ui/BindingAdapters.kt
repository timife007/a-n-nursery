package com.timife.a_n_nursery_app.inventory.ui

import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.inventory.response.Result
import com.timife.a_n_nursery_app.vendor.VendorAdapter
import com.timife.a_n_nursery_app.vendor.response.VendorItem

fun bindRecyclerView(recyclerView: RecyclerView, data: List<Result>) {
    val adapter = recyclerView.adapter as InventAdapter
    adapter.submitList(data)
}

fun bindVenRecyclerView(recyclerView: RecyclerView, data: List<VendorItem>) {
    val adapter = recyclerView.adapter as VendorAdapter
    adapter.submitList(data)
}

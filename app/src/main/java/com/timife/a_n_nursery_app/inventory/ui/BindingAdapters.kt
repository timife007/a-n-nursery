package com.timife.a_n_nursery_app.inventory.ui

import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.inventory.response.Result

fun bindRecyclerView(recyclerView: RecyclerView, data: List<Result>) {
    val adapter = recyclerView.adapter as InventAdapter
    adapter.submitList(data)
}

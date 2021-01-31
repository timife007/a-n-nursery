package com.timife.a_n_nursery_app.vendor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.VendorItemBinding
import com.timife.a_n_nursery_app.vendor.response.VendorItem

class VendorAdapter(
    var item: ArrayList<VendorItem>
) : RecyclerView.Adapter<VendorAdapter.VendorViewHolder>() {
    inner class VendorViewHolder(private var binding: VendorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vendorItem: VendorItem) {
            binding.vendorsItem = vendorItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        return VendorViewHolder(VendorItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        val vendorItem =item[position]
        holder.bind(vendorItem)
    }
    override fun getItemCount(): Int {
        return item.size
    }
}

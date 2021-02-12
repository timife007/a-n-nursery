package com.timife.a_n_nursery_app.vendor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.VendorItemBinding
import com.timife.a_n_nursery_app.vendor.response.VendorItem

class VendorAdapter(
        private val onClickListener: OnClickListener
) : PagingDataAdapter<VendorItem, VendorAdapter.VendorViewHolder>(VENDOR_COMPARATOR) {
    inner class VendorViewHolder(private var binding: VendorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vendorItem: VendorItem) {
            binding.vendorItem = vendorItem
            binding.executePendingBindings()
        }
    }

    companion object {
        private val VENDOR_COMPARATOR = object : DiffUtil.ItemCallback<VendorItem>() {
            override fun areItemsTheSame(oldItem: VendorItem, newItem: VendorItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: VendorItem, newItem: VendorItem) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        return VendorViewHolder(VendorItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        val vendor =getItem(position)
        if (vendor != null) {
            holder.bind(vendor)
        }
        holder.itemView.setOnClickListener {
            if (vendor != null) {
                onClickListener.onClick(vendor)
            }
        }
    }

    class OnClickListener(val clickListener: (vendor: VendorItem) -> Unit) {
        fun onClick(vendor: VendorItem) {
            clickListener(vendor)
        }
    }
}

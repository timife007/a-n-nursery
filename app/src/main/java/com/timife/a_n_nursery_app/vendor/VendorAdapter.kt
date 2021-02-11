package com.timife.a_n_nursery_app.vendor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.InventoryCardItemBinding
import com.timife.a_n_nursery_app.databinding.VendorItemBinding
import com.timife.a_n_nursery_app.inventory.response.Result
import com.timife.a_n_nursery_app.vendor.response.VendorItem
import java.nio.file.Files.size

class VendorAdapter(
        private val onClickListener: OnClickListener
) : ListAdapter<VendorItem, VendorAdapter.VendorViewHolder>(DiffCallback) {
    inner class VendorViewHolder(private var binding: VendorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vendorItem: VendorItem) {
            binding.vendorItem = vendorItem
            binding.executePendingBindings()
        }
    }
    companion object DiffCallback : DiffUtil.ItemCallback<VendorItem>() {
        override fun areItemsTheSame(oldItem: VendorItem, newItem: VendorItem): Boolean {
            return newItem === oldItem
        }


        override fun areContentsTheSame(oldItem: VendorItem, newItem: VendorItem): Boolean {
            return newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        return VendorViewHolder(VendorItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        val vendor =getItem(position)
        holder.bind(vendor)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(vendor)
        }
    }

    class OnClickListener(val clickListener: (vendor: VendorItem) -> Unit) {
        fun onClick(vendor: VendorItem) {
            clickListener(vendor)
        }
    }

//    override fun getItemCount() = listOf<VendorItem>().size ?: 0

}

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
import okio.`-DeprecatedUtf8`.size
import java.nio.file.Files.size

class VendorAdapter(
        private val onClickListener: OnClickListener
) : ListAdapter<VendorItem, VendorAdapter.VendorViewHolder>(DiffCallback) {
    inner class VendorViewHolder(private var binding: VendorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vendorItem: VendorItem) {
            binding.vendorsItem = vendorItem
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
        val inventoryProduct =getItem(position)
        holder.bind(inventoryProduct)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(inventoryProduct)
        }

    }

    class OnClickListener(val clickListener: (product: VendorItem) -> Unit) {
        fun onClick(product: VendorItem) {
            clickListener(product)
        }
    }

    override fun getItemCount() = listOf<VendorItem>().size ?: 0

}

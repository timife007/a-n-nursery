package com.timife.a_n_nursery_app.inventory.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.InventoryCardItemBinding
import com.timife.a_n_nursery_app.inventory.response.Result

class InventAdapter(
    private val onClickListener: OnClickListener
) : ListAdapter<Result, InventAdapter.InventViewHolder>(DiffCallback) {
    inner class InventViewHolder(private var binding: InventoryCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(inventoryProducts: Result) {
            binding.inventoryResult = inventoryProducts
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventViewHolder {
        return InventViewHolder(InventoryCardItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: InventViewHolder, position: Int) {
        val inventoryProduct = getItem(position)
        holder.bind(inventoryProduct)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(inventoryProduct)
        }
    }

    class OnClickListener(val clickListener: (product: Result) -> Unit) {
        fun onClick(product: Result) {
            clickListener(product)
        }
    }
}

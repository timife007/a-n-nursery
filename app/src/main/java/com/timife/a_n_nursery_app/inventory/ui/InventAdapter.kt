package com.timife.a_n_nursery_app.inventory.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.InventoryCardItemBinding
import com.timife.a_n_nursery_app.inventory.response.Result

class InventAdapter(
    private val onClickListener: OnClickListener
) : PagingDataAdapter<Result, InventAdapter.InventViewHolder>(INVENTORY_COMPARATOR) {
    inner class InventViewHolder(private var binding: InventoryCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(inventoryProducts: Result) {
            val dropdown = binding.dropdown
            dropdown.setOnClickListener {
            }
            binding.inventoryResult = inventoryProducts
            binding.executePendingBindings()

        }
    }

    companion object {
        private val INVENTORY_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Result, newItem: Result) =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventViewHolder {
        return InventViewHolder(
            InventoryCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InventViewHolder, position: Int) {
        val inventoryProduct = getItem(position)
        if (inventoryProduct != null) {
            holder.bind(inventoryProduct)
        }
        holder.itemView.setOnClickListener {
            if (inventoryProduct != null) {
                onClickListener.onClick(inventoryProduct)
            }
        }
    }

    class OnClickListener(val clickListener: (product: Result) -> Unit) {
        fun onClick(product: Result) {
            clickListener(product)
        }
    }
}

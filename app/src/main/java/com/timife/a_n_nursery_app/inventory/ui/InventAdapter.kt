package com.timife.a_n_nursery_app.inventory.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.InventoryItemBinding
import com.timife.a_n_nursery_app.inventory.response.InventoryProductsItem

class InventAdapter(
        var item: ArrayList<InventoryProductsItem>
//var item: List<InventoryItem>,
//private val viewModel: InventoryViewModel
) : RecyclerView.Adapter<InventAdapter.InventViewHolder>() {
   inner class InventViewHolder(private var binding: InventoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(inventoryProducts: InventoryProductsItem) {
            binding.inventoryProducts = inventoryProducts
           binding.executePendingBindings()
//           binding.initials.text = item.productName
       }
   }
//    companion object DiffCallback : DiffUtil.ItemCallback<InventoryProductsItem>() {
//        override fun areItemsTheSame(oldItem: InventoryProductsItem, newItem: InventoryProductsItem): Boolean {
//            return newItem === oldItem
//        }
//
//        override fun areContentsTheSame(oldItem: InventoryProductsItem, newItem: InventoryProductsItem): Boolean {
//            return newItem == oldItem
//        }
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventViewHolder {
        return InventViewHolder(InventoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: InventViewHolder, position: Int) {
        val inventoryProduct =item[position]
        holder.bind(inventoryProduct)
//        val curShopItem = item[position]
//        holder.bind(curShopItem)
    }
//
    override fun getItemCount(): Int {
        return item.size
    }
}

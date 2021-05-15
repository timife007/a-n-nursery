package com.timife.a_n_nursery_app.inventory.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.timife.a_n_nursery_app.databinding.InventoryCardItemBinding
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationAdapter
import com.timife.a_n_nursery_app.inventory.response.Inventory
import kotlinx.android.synthetic.main.inventory_card_item.view.*

class InventAdapter(
    private val context: Context,
    private val onClickListener: OnClickListener, private val onDeleteListener: OnDeleteListener
) : PagingDataAdapter<Inventory, InventAdapter.InventViewHolder>(INVENTORY_COMPARATOR) {
    inner class InventViewHolder(private var binding: InventoryCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(inventoryProducts: Inventory) {

            binding.inventoryResult = inventoryProducts
            binding.executePendingBindings()
        }
    }

    companion object {
        private val INVENTORY_COMPARATOR = object : DiffUtil.ItemCallback<Inventory>() {
            override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory) =
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
        holder.itemView.dropdown.setOnClickListener {
            val inventoryItemOptions = arrayOf("Delete")
            MaterialAlertDialogBuilder(context).setTitle("")
                .setItems(inventoryItemOptions) { dialog,
                                                  which ->
                        MaterialAlertDialogBuilder(context).setTitle("Delete Item").setMessage("Do you want to completely delete this classification?").setNegativeButton("No"){
                                dialog, _ ->
                            dialog.dismiss()
                        }.setPositiveButton("Yes"){
                                dialog, _ ->
                            onDeleteListener.delete(inventoryProduct!!)
                            notifyDataSetChanged()
                            dialog.dismiss()
                        }.show()
                }.show()
        }
    }

    class OnClickListener(val clickListener: (product: Inventory) -> Unit) {
        fun onClick(product: Inventory) {
            clickListener(product)
        }
    }


    class OnDeleteListener(val deleteListener: (Int) -> Unit) {
        fun delete(product: Inventory) {
            deleteListener(product.id)
        }
    }


}

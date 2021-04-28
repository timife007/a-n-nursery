package com.timife.a_n_nursery_app.inventory.lots.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.timife.a_n_nursery_app.databinding.LocationItemBinding
import com.timife.a_n_nursery_app.databinding.LotItemBinding
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationAdapter
import com.timife.a_n_nursery_app.inventory.lots.database.LotItem
import com.timife.a_n_nursery_app.inventory.lots.network.Lot
import kotlinx.android.synthetic.main.location_item.view.*
import kotlinx.android.synthetic.main.lot_item.view.*


class LotAdapter(private val onClickListener: OnClickListener, val onDeleteListener:OnDeleteListener, val context: Context) :
    androidx.recyclerview.widget.ListAdapter<Lot, LotAdapter.LotViewHolder>(DiffCallback) {
    class LotViewHolder(private var binding: LotItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lot: Lot) {
            binding.result = lot
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Lot>() {
        override fun areItemsTheSame(oldItem: Lot, newItem: Lot): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Lot, newItem: Lot): Boolean {
            return newItem == oldItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LotViewHolder {
        return LotViewHolder(LotItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: LotViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
        holder.itemView.edit_item_lot.setOnClickListener {
            onClickListener.onClick(result)
        }

        holder.itemView.delete_lot.setOnClickListener {
            MaterialAlertDialogBuilder(context).setTitle("Delete Item").setMessage("Do you want to completely delete this lot?").setNegativeButton("No"){
                    dialog, which ->
                dialog.dismiss()
            }.setPositiveButton("Yes"){
                    dialog, which ->
                onDeleteListener.delete(result)
                notifyDataSetChanged()
                dialog.dismiss()
            }.show()

        }
    }

    class OnClickListener(val clickListener: (lot: Lot) -> Unit) {
        fun onClick(lot: Lot) {
            clickListener(lot)
        }
    }

    class OnDeleteListener(val deleteListener:(Int) ->Unit){
        fun delete(lot: Lot){
            deleteListener(lot.id!!)
        }
    }
}
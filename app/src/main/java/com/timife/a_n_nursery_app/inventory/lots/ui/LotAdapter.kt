package com.timife.a_n_nursery_app.inventory.lots.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.LocationItemBinding
import com.timife.a_n_nursery_app.databinding.LotItemBinding
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import com.timife.a_n_nursery_app.inventory.lots.database.LotItem
import com.timife.a_n_nursery_app.inventory.lots.network.Lot
import kotlinx.android.synthetic.main.lot_item.view.*


class LotAdapter(private val onClickListener: OnClickListener) :
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
    }

    class OnClickListener(val clickListener: (lot: Lot) -> Unit) {
        fun onClick(lot: Lot) {
            clickListener(lot)
        }
    }
}
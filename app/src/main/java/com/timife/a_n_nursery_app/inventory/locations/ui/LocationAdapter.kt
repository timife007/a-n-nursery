package com.timife.a_n_nursery_app.inventory.locations.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.timife.a_n_nursery_app.databinding.LocationItemBinding
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryAdapter
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.location_item.view.*


class LocationAdapter(private val onClickListener: OnClickListener, val onDeleteListener:OnDeleteListener, val context: Context) :
    androidx.recyclerview.widget.ListAdapter<Location, LocationAdapter.LocationViewHolder>(DiffCallback) {
    class LocationViewHolder(private var binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location) {
            binding.result = location
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return newItem == oldItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationViewHolder {
        return LocationViewHolder(LocationItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
        holder.itemView.edit_item_location.setOnClickListener {
            onClickListener.onClick(result)
        }

        holder.itemView.delete_location.setOnClickListener {
            MaterialAlertDialogBuilder(context).setTitle("Delete Item").setMessage("Do you want to completely delete this location?").setNegativeButton("No"){
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

    class OnClickListener(val clickListener: (location: Location) -> Unit) {
        fun onClick(location: Location) {
            clickListener(location)
        }
    }

    class OnDeleteListener(val deleteListener:(Int) ->Unit){
        fun delete(location: Location){
            deleteListener(location.id!!)
        }
    }
}
package com.timife.a_n_nursery_app.inventory.locations.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.LocationItemBinding
import com.timife.a_n_nursery_app.inventory.locations.network.Location
import kotlinx.android.synthetic.main.location_item.view.*


class LocationAdapter(private val onClickListener: OnClickListener) :
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
    }

    class OnClickListener(val clickListener: (location: Location) -> Unit) {
        fun onClick(location: Location) {
            clickListener(location)
        }
    }
}
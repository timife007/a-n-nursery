package com.timife.a_n_nursery_app.inventory.categories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.CategoryItemBinding
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter(private val onClickListener: OnClickListener) :
    androidx.recyclerview.widget.ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallback) {
    class CategoryViewHolder(private var binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.result = category
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return newItem == oldItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
        holder.itemView.edit_item_category.setOnClickListener {
            onClickListener.onClick(result)
        }
    }



    class OnClickListener(val clickListener: (category: Category) -> Unit) {
        fun onClick(category: Category) {
            clickListener(category)
        }
    }
}
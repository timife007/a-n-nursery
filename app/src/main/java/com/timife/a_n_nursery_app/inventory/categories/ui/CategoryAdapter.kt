package com.timife.a_n_nursery_app.inventory.categories.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.timife.a_n_nursery_app.ViewModelFactory
import com.timife.a_n_nursery_app.databinding.CategoryItemBinding
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import kotlinx.android.synthetic.main.category_item.view.*
import kotlin.coroutines.coroutineContext

class CategoryAdapter(private val onClickListener: OnClickListener,val onDeleteListener:OnDeleteListener,val context:Context) :
    androidx.recyclerview.widget.ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallback) {

    inner class CategoryViewHolder(private var binding: CategoryItemBinding) :
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

        holder.itemView.delete.setOnClickListener {
            MaterialAlertDialogBuilder(context).setTitle("Delete Item").setMessage("Do you want to completely delete this item?").setNegativeButton("No"){
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



    class OnClickListener(val clickListener: (category: Category) -> Unit) {
        fun onClick(category: Category) {
            clickListener(category)
        }
    }

    class OnDeleteListener(val deleteListener:(Int) ->Unit){
        fun delete(category: Category){
            deleteListener(category.id!!)
        }
    }
}
package com.timife.a_n_nursery_app.inventory.classifications.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.timife.a_n_nursery_app.databinding.CategoryItemBinding
import com.timife.a_n_nursery_app.databinding.ClassificationItemBinding
import com.timife.a_n_nursery_app.inventory.categories.network.Category
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryAdapter
import com.timife.a_n_nursery_app.inventory.classifications.network.Classification
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.classification_item.view.*

class ClassificationAdapter(private val onClickListener: OnClickListener, val onDeleteListener:OnDeleteListener, val context: Context) :
    androidx.recyclerview.widget.ListAdapter<Classification, ClassificationAdapter.ClassificationViewHolder>(DiffCallback) {
    class ClassificationViewHolder(private var binding: ClassificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(classification: Classification) {
            binding.result = classification
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Classification>() {
        override fun areItemsTheSame(oldItem: Classification, newItem: Classification): Boolean {
            return newItem === oldItem
        }

        override fun areContentsTheSame(oldItem: Classification, newItem: Classification): Boolean {
            return newItem == oldItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassificationViewHolder {
        return ClassificationViewHolder(ClassificationItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ClassificationViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
        holder.itemView.edit_item_classification.setOnClickListener {
            onClickListener.onClick(result)
        }

        holder.itemView.delete_classification.setOnClickListener {
            MaterialAlertDialogBuilder(context).setTitle("Delete Item").setMessage("Do you want to completely delete this classification?").setNegativeButton("No"){
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

    class OnClickListener(val clickListener: (classification: Classification) -> Unit) {
        fun onClick(classification: Classification) {
            clickListener(classification)
        }
    }


    class OnDeleteListener(val deleteListener:(Int) ->Unit){
        fun delete(classification: Classification){
            deleteListener(classification.id)
        }
    }
}
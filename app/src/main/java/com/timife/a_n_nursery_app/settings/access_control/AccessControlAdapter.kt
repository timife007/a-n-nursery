package com.timife.a_n_nursery_app.settings.access_control

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.AccessItemBinding
import com.timife.a_n_nursery_app.settings.access_control.response.Invitee

class AccessControlAdapter(
    private val context: Context,
//    private val onClickListener: OnClickListener
) : PagingDataAdapter<Invitee, AccessControlAdapter.InviteeViewHolder>(INVITEE_COMPARATOR) {
    inner class InviteeViewHolder(private var binding: AccessItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(invitee: Invitee) {
            val dropdown = binding.dropdown
            dropdown.setOnClickListener {

            }
            binding.inviteeResult = invitee
            binding.executePendingBindings()

        }
    }

    companion object {
        private val INVITEE_COMPARATOR = object : DiffUtil.ItemCallback<Invitee>() {
            override fun areItemsTheSame(oldItem: Invitee, newItem: Invitee) =
                oldItem.email== newItem.email

            override fun areContentsTheSame(oldItem: Invitee, newItem: Invitee) =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InviteeViewHolder {
        return InviteeViewHolder(
            AccessItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InviteeViewHolder, position: Int) {
        val invitee = getItem(position)

        if (invitee != null) {
            holder.bind(invitee)
        }
//        holder.itemView.setOnClickListener {
//            if (invitee != null) {
//                onClickListener.onClick(inventoryProduct)
//            }
//        }
//        holder.itemView.dropdown.setOnClickListener {
//            val inventoryItemOptions = arrayOf("Edit")
//            MaterialAlertDialogBuilder(context).setTitle("").setItems(inventoryItemOptions){ dialog,
//                which -> if (inventoryProduct != null) {
//                        onClickListener.onClickEdit(inventoryProduct)
//                    }
//            }.show()
//        }
    }

//    class OnClickListener(val clickListener: (product: Inventory) -> Unit) {
//        fun onClick(product: Inventory) {
//            clickListener(product)
//        }
//        fun onClickEdit(product: Inventory){
//            clickListener(product)
//        }
//    }


}
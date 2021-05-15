package com.timife.a_n_nursery_app.activities

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.activities.response.Activity
import com.timife.a_n_nursery_app.databinding.ActivitiesItemBinding
import java.text.SimpleDateFormat

class ActivitiesAdapter(
    private val context: Context
//    private val onClickListener: OnClickListener
) : PagingDataAdapter<Activity, ActivitiesAdapter.ActivityViewHolder>(ACTIVITY_COMPARATOR) {
    inner class ActivityViewHolder(private var binding: ActivitiesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(activityItems: Activity) {
            binding.activityResult = activityItems
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
            val date = sdf.parse(activityItems.created)
            binding.created.text = date?.toString()
            binding.executePendingBindings()
        }
    }

    companion object {
        private val ACTIVITY_COMPARATOR = object : DiffUtil.ItemCallback<Activity>() {
            override fun areItemsTheSame(oldItem: Activity, newItem: Activity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Activity, newItem: Activity) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(
            ActivitiesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activityItem = getItem(position)

        if (activityItem != null) {
            holder.bind(activityItem)
        }
    }

    class OnClickListener(val clickListener: (activity: Activity) -> Unit) {
        fun onClick(activity: Activity) {
            clickListener(activity)
        }

    }


}

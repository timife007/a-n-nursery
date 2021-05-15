package com.timife.a_n_nursery_app.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.ActivitiesLoadStateFooterBinding
import com.timife.a_n_nursery_app.databinding.InventoryLoadStateFooterBinding
import com.timife.a_n_nursery_app.inventory.ui.InventoryLoadStateAdapter

class ActivitiesLoadStateAdapter (private val retry: () -> Unit): LoadStateAdapter<ActivitiesLoadStateAdapter.LoadStateViewHolder>(){
    inner class LoadStateViewHolder(private val binding: ActivitiesLoadStateFooterBinding, retry: () -> Unit
    ):
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState){
            binding.apply {

//                if (loadState is LoadState.Error) {
//                    errorText.isVisible  = true
//                }
                footerProgressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
                errorText.isVisible = loadState !is LoadState.Loading


//                if (loadState.endOfPaginationReached ){
//                    retryButton.isVisible = false
//                    errorText.isVisible = false
//                }
            }
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding  = ActivitiesLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return LoadStateViewHolder(binding,retry)
    }
}
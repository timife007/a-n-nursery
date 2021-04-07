package com.timife.a_n_nursery_app.settings.access_control

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.AccessLoadstateFooterBinding

class AccessLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<AccessLoadStateAdapter.LoadStateViewHolder>(){
    inner class LoadStateViewHolder(private val binding: AccessLoadstateFooterBinding):
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState){
            binding.apply {
                footerProgressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorText.isVisible = loadState is LoadState.Error


                if (loadState.endOfPaginationReached ){
                    retryButton.isVisible = false
                    errorText.isVisible = false
                }
            }
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding  = AccessLoadstateFooterBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return LoadStateViewHolder(binding)
    }
}
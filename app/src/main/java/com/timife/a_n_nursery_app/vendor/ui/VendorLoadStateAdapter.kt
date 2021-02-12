package com.timife.a_n_nursery_app.vendor.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timife.a_n_nursery_app.databinding.VendorLoadStateFooterBinding

class VendorLoadStateAdapter (private val retry: () -> Unit): LoadStateAdapter<VendorLoadStateAdapter.LoadStateViewHolder>(){
    inner class LoadStateViewHolder(private val binding: VendorLoadStateFooterBinding):
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState){
            binding.apply {
                footerVenProgressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorText.isVisible = loadState is LoadState.Error

            }
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding  = VendorLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return LoadStateViewHolder(binding)
    }
}
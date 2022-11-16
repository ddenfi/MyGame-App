package com.ddenfi.expertcapstone.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddenfi.expertcapstone.core.databinding.ItemGameLoadingBinding
import com.ddenfi.expertcapstone.core.databinding.ItemGamePlaceholderBinding

class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    inner class LoadingStateViewHolder(
        private val binding: ItemGameLoadingBinding,
        retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> {
                    binding.pgGame.visibility = View.VISIBLE
                    binding.btnRetry.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is LoadState.Error -> {
                    binding.tvError.text = loadState.error.localizedMessage
                    binding.pgGame.visibility = View.VISIBLE
                    binding.btnRetry.visibility = View.VISIBLE
                    binding.tvError.visibility = View.VISIBLE
                }
                is LoadState.NotLoading -> {
                    binding.pgGame.visibility = View.GONE
                    binding.btnRetry.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
            }
//            if (loadState is LoadState.Error) {
//                binding.tvError.text = loadState.error.localizedMessage
//            }
//            binding.pgGame.isVisible = loadState is LoadState.Loading
//            binding.pgGame.isVisible = loadState is LoadState.Error
//            binding.pgGame.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding =
            ItemGameLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }
}
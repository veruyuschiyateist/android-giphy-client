package com.gph.tst.giphytestapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gph.tst.giphytestapp.databinding.LoadStateBinding

class GiphyLoadStateAdapter : LoadStateAdapter<GiphyLoadStateAdapter.GiphyLoadStateHolder>() {

    override fun onBindViewHolder(holder: GiphyLoadStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): GiphyLoadStateHolder {
        val binding =
            LoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GiphyLoadStateHolder(binding)
    }

    class GiphyLoadStateHolder(private val binding: LoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}
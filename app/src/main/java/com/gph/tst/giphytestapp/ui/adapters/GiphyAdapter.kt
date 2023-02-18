package com.gph.tst.giphytestapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.databinding.GiphyListItemBinding
import com.gph.tst.giphytestapp.ui.adapters.GiphyAdapter.GiphyViewHolder
import kotlin.math.sqrt

typealias OnLongClickItemListener = (GiphyLocalEntity) -> Unit

class GiphyAdapter(
    private val listener: OnLongClickItemListener,
) : PagingDataAdapter<GiphyLocalEntity, GiphyViewHolder>(GiphyDiffCallback()) {

    inner class GiphyViewHolder(
        private val binding: GiphyListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            giphyLocalEntity: GiphyLocalEntity?,
        ) {

            binding.root.setOnLongClickListener {
                listener.invoke(giphyLocalEntity!!)
                return@setOnLongClickListener true
            }
            
            Glide.with(binding.root.context)
                .load(giphyLocalEntity?.url)
                .placeholder(R.drawable.cat1)
                .into(binding.imageView)

        }
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val binding =
            GiphyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GiphyViewHolder(binding)
    }
    
    companion object {
        private const val TAG = "GiphyAdapter"
    }
}

class GiphyDiffCallback : DiffUtil.ItemCallback<GiphyLocalEntity>() {
    override fun areItemsTheSame(oldItem: GiphyLocalEntity, newItem: GiphyLocalEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GiphyLocalEntity, newItem: GiphyLocalEntity): Boolean {
        return oldItem == newItem
    }

}
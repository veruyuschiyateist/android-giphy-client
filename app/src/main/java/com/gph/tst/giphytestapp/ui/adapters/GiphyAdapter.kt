package com.gph.tst.giphytestapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.databinding.GiphyListItemBinding
import com.gph.tst.giphytestapp.ui.adapters.GiphyAdapter.GiphyViewHolder

typealias OnLongClickItemListener = (GiphyLocalEntity) -> Unit
typealias OnClickItemListener = (Int) -> Unit

class GiphyAdapter(
    private val longClickListener: OnLongClickItemListener,
    private val clickListener: OnClickItemListener
) : PagingDataAdapter<GiphyLocalEntity, GiphyViewHolder>(GiphyDiffCallback()) {

    inner class GiphyViewHolder(
        private val binding: GiphyListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            giphyLocalEntity: GiphyLocalEntity?,
            position: Int
        ) {

            binding.root.setOnLongClickListener {
                longClickListener.invoke(giphyLocalEntity!!)
                return@setOnLongClickListener true
            }

            binding.root.setOnClickListener {
                clickListener.invoke(position)
            }
            
            Glide.with(binding.root.context)
                .load(giphyLocalEntity?.url)
                .placeholder(R.drawable.cat1)
                .into(binding.imageView)

        }
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        holder.bind(getItem(position), position)
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
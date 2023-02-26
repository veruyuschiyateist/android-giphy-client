package com.gph.tst.giphytestapp.ui.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.databinding.GiphyListItemBinding
import com.gph.tst.giphytestapp.domain.entity.GifModel

class FavouriteGifsAdapter(
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<FavouriteGifsAdapter.ViewHolder>() {

    var gifs = emptyList<GifModel>()

    inner class ViewHolder(val binding: GiphyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            gifModel: GifModel,
        ) {
            Glide.with(binding.root.context)
                .load(gifModel.url)
                .placeholder(R.drawable.cat1)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                clickListener.invoke(gifModel.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GiphyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = gifs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gifs[position])
    }
}
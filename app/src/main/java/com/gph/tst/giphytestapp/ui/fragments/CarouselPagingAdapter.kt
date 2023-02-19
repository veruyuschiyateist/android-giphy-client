package com.gph.tst.giphytestapp.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.databinding.CarouselListItemBinding
import com.gph.tst.giphytestapp.ui.adapters.GiphyDiffCallback
import kotlin.math.roundToInt

class CarouselPagingAdapter :
    PagingDataAdapter<GiphyLocalEntity, CarouselPagingAdapter.CarouselPagingViewHolder>(
        GiphyDiffCallback()
    ) {

    private var hasInitParentDimensions = false
    private var maxImageWidth: Int = 0
    private var maxImageHeight: Int = 0
    private var maxImageAspectRatio: Float = 1f

    inner class CarouselPagingViewHolder(
        val binding: CarouselListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            giphyLocalEntity: GiphyLocalEntity?,
        ) {
            Glide.with(binding.root.context)
                .load(giphyLocalEntity?.url)
                .placeholder(R.drawable.cat1)
                .into(binding.imageView)
        }
    }

    override fun onBindViewHolder(holder: CarouselPagingViewHolder, position: Int) {
        val image = getItem(position)

        val imageAspectRatio = image!!.aspectRatio

        val targetImageWidth: Int =
            if (imageAspectRatio < maxImageAspectRatio) {
                (maxImageHeight * imageAspectRatio).roundToInt()
            } else {
                maxImageWidth
            }

        holder.binding.imageView.layoutParams = RecyclerView.LayoutParams(
            targetImageWidth,
            RecyclerView.LayoutParams.MATCH_PARENT
        )

        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselPagingViewHolder {
        val binding =
            CarouselListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        if (!hasInitParentDimensions) {
            maxImageWidth = (parent.width * 0.75f).roundToInt()
            maxImageHeight = parent.height
            maxImageAspectRatio = maxImageWidth.toFloat() / maxImageHeight.toFloat()
            hasInitParentDimensions = true
        }

        return CarouselPagingViewHolder(binding)
    }

    companion object {
        private const val TAG = "GiphyAdapter"
    }
}
package com.gph.tst.giphytestapp.ui.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gph.tst.giphytestapp.databinding.CarouselListItemBinding
import com.gph.tst.giphytestapp.databinding.GiphyListItemBinding
import kotlin.math.roundToInt

internal class CarouselAdapter(private val images: List<ImageItem>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    private var hasInitParentDimensions = false
    private var maxImageWidth: Int = 0
    private var maxImageHeight: Int = 0
    private var maxImageAspectRatio: Float = 1f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {

        val binding =
            CarouselListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        if (!hasInitParentDimensions) {
            maxImageWidth = (parent.width * 0.75f).roundToInt()
            maxImageHeight = parent.height
            maxImageAspectRatio = maxImageWidth.toFloat() / maxImageHeight.toFloat()
            hasInitParentDimensions = true
        }

        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: CarouselViewHolder, position: Int) {
        val image = images[position]

        val imageAspectRatio = image.aspectRatio

        val targetImageWidth: Int =
            if (imageAspectRatio < maxImageAspectRatio) {
                (maxImageHeight * imageAspectRatio).roundToInt()
            } else {
                maxImageWidth
            }

        viewHolder.binding.imageView.layoutParams = RecyclerView.LayoutParams(
            targetImageWidth,
            RecyclerView.LayoutParams.MATCH_PARENT
        )

        viewHolder.binding.imageView.setImageResource(image.id)
    }

    override fun getItemCount(): Int = images.size

    class CarouselViewHolder(val binding: CarouselListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
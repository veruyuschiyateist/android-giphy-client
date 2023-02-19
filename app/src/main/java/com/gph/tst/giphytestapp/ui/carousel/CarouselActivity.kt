package com.gph.tst.giphytestapp.ui.carousel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.databinding.ActivityCarouselBinding

class CarouselActivity : AppCompatActivity() {

    private val binding: ActivityCarouselBinding by lazy {
        ActivityCarouselBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var snapHelper: SnapHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupList()
    }

    private fun setupList() {

        val images = listOf(
            ImageItem(R.drawable.cat1),
            ImageItem(R.drawable.cat2),
            ImageItem(R.drawable.cat3),
            ImageItem(R.drawable.cat4),
            ImageItem(R.drawable.cat5),
            ImageItem(R.drawable.cat6),
            ImageItem(R.drawable.cat7),
            ImageItem(R.drawable.cat8),
            ImageItem(R.drawable.cat9)
        )

        binding.carouselRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@CarouselActivity, LinearLayoutManager.HORIZONTAL, false)

            this.adapter = CarouselAdapter(images = images)

            addItemDecoration(
                CarouselItemDecoration(
                    innerSpacing = resources.getDimensionPixelSize(R.dimen.carousel_spacing)
                )
            )
            addItemDecoration(BoundsOffsetDecoration())
        }

        snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.carouselRecyclerView)

        initRecyclerViewPosition(position = 3)
    }

    private fun initRecyclerViewPosition(position: Int) {

        val layoutManager = binding.carouselRecyclerView.layoutManager as LinearLayoutManager

        layoutManager.scrollToPosition(position)

        binding.carouselRecyclerView.doOnPreDraw {
            val targetView = binding.carouselRecyclerView.layoutManager?.findViewByPosition(position)
                ?: return@doOnPreDraw

            val distanceToFinalSnap =
                snapHelper.calculateDistanceToFinalSnap(layoutManager, targetView)
                    ?: return@doOnPreDraw

            layoutManager.scrollToPositionWithOffset(position, -distanceToFinalSnap[0])
        }
    }
}
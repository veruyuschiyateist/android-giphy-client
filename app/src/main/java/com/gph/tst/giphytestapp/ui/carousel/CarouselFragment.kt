package com.gph.tst.giphytestapp.ui.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.databinding.FragmentCarouselBinding
import com.gph.tst.giphytestapp.ui.adapters.CarouselPagingAdapter
import com.gph.tst.giphytestapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

private const val CURRENT_GIF_PARAM = "param1"

@AndroidEntryPoint
class CarouselFragment : Fragment() {
    private var currentGifParam: Int? = null

    private lateinit var binding: FragmentCarouselBinding
    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var snapHelper: SnapHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentGifParam = it.getInt(CURRENT_GIF_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCarouselBinding.inflate(inflater, container, false)

        setupList(currentGifParam ?: 0)

        return binding.root
    }

    private fun setupList(position: Int) {
        val adapter = CarouselPagingAdapter()
        val recyclerView = binding.carouselRecyclerView

        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

            this.adapter = adapter

            addItemDecoration(
                CarouselItemDecoration(
                    innerSpacing = resources.getDimensionPixelSize(
                        R.dimen.carousel_spacing
                    )
                )
            )
            addItemDecoration(BoundsOffsetDecoration())
        }

        snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.carouselRecyclerView)

        observeGifs(adapter)

        initRecyclerViewPosition(position)
    }

    private fun initRecyclerViewPosition(position: Int) {

        val layoutManager = binding.carouselRecyclerView.layoutManager as LinearLayoutManager

        layoutManager.scrollToPosition(position)

        binding.carouselRecyclerView.doOnPreDraw {
            val targetView =
                binding.carouselRecyclerView.layoutManager?.findViewByPosition(position)
                    ?: return@doOnPreDraw

            val distanceToFinalSnap =
                snapHelper.calculateDistanceToFinalSnap(layoutManager, targetView)
                    ?: return@doOnPreDraw

            layoutManager.scrollToPositionWithOffset(position, -distanceToFinalSnap[0])
        }
    }

    private fun observeGifs(adapter: CarouselPagingAdapter) {
        lifecycleScope.launchWhenStarted {
            viewModel.giphyFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(currentGif: Int) =
            CarouselFragment().apply {
                arguments = bundleOf(CURRENT_GIF_PARAM to currentGif)
            }
    }
}
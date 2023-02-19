package com.gph.tst.giphytestapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.databinding.FragmentCarouselBinding
import com.gph.tst.giphytestapp.databinding.FragmentHomeBinding
import com.gph.tst.giphytestapp.ui.adapters.GiphyAdapter
import com.gph.tst.giphytestapp.ui.carousel.BoundsOffsetDecoration
import com.gph.tst.giphytestapp.ui.carousel.CarouselAdapter
import com.gph.tst.giphytestapp.ui.carousel.CarouselItemDecoration
import com.gph.tst.giphytestapp.ui.carousel.ImageItem
import com.gph.tst.giphytestapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint

class CarouselFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentCarouselBinding
    private val viewModel by activityViewModels<HomeViewModel>()

    private lateinit var snapHelper: SnapHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCarouselBinding.inflate(inflater, container, false)

        setupList()

        return binding.root
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
        val adapter = CarouselPagingAdapter()

        binding.carouselRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

//            this.adapter = CarouselAdapter(images = images)
            this.adapter = adapter

            addItemDecoration(
                CarouselItemDecoration(
                    innerSpacing = resources.getDimensionPixelSize(R.dimen.carousel_spacing)
                )
            )
            addItemDecoration(BoundsOffsetDecoration())
        }

        snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.carouselRecyclerView)

        observeGifs(adapter)

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

    private fun observeGifs(adapter: CarouselPagingAdapter) {
        lifecycleScope.launchWhenStarted {
            viewModel.giphyFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CarouselFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
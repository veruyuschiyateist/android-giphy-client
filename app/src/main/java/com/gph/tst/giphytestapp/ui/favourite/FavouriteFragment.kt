package com.gph.tst.giphytestapp.ui.favourite

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.databinding.FragmentFavouriteBinding
import com.gph.tst.giphytestapp.ui.home.adapters.GiphyAdapter
import com.gph.tst.giphytestapp.ui.home.adapters.GiphyLoadStateAdapter
import com.gph.tst.giphytestapp.ui.home.confirmation.RemoveGifAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding

    private val viewModel by viewModels<FavouriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)

        setupList()

        return binding.root
    }

    private fun setupList() {
        val adapter = FavouriteGifsAdapter(
            clickListener = { gifId ->
                Log.d(TAG, "setupList: $gifId")
                viewModel.remove(gifId)
            }
        )
        val layoutManager = GridLayoutManager(requireActivity(), 2)

        binding.recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
            setHasFixedSize(true)
        }

        observeGifs(adapter)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeGifs(adapter: FavouriteGifsAdapter) {
        lifecycleScope.launchWhenStarted {
            viewModel.gifStateFlow.collect {
                adapter.gifs = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        private const val TAG = "FavouriteFragment"
    }
}
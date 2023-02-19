package com.gph.tst.giphytestapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.databinding.FragmentHomeBinding
import com.gph.tst.giphytestapp.ui.adapters.GiphyAdapter
import com.gph.tst.giphytestapp.ui.adapters.GiphyLoadStateAdapter
import com.gph.tst.giphytestapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupList()
        setupSearchField()

        return binding.root
    }

    private fun setupList() {
        val adapter = GiphyAdapter(
            longClickListener = {
                viewModel.remove(it)
            },
            clickListener = {
                requireActivity().supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<CarouselFragment>(R.id.fragment_container_view)
                }
            }
        )

        val footerAdapter = GiphyLoadStateAdapter()

        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
        val layoutManager = GridLayoutManager(requireActivity(), 2)

        binding.recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapterWithLoadState
            setHasFixedSize(true)
        }

        observeGifs(adapter)
    }

    private fun setupSearchField() {
        binding.queryEditText.editText?.addTextChangedListener {
            viewModel.setQuery(query = it.toString().trim())
        }
    }

    private fun observeGifs(adapter: GiphyAdapter) {
        lifecycleScope.launchWhenStarted {
            viewModel.giphyFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }


}
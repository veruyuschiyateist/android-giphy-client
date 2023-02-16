package com.gph.tst.giphytestapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gph.tst.giphytestapp.databinding.ActivityHomeBinding
import com.gph.tst.giphytestapp.ui.adapters.GiphyAdapter
import com.gph.tst.giphytestapp.ui.adapters.GiphyLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupList()
        setupSearchField()
    }

    private fun setupList() {
        val adapter = GiphyAdapter()
        val footerAdapter = GiphyLoadStateAdapter()

        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapterWithLoadState
        }

        observeGifs(adapter)
    }

    private fun setupSearchField() {
        binding.queryEditText.addTextChangedListener {
            viewModel.setQuery(query = it.toString())
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
        private const val TAG = "MainActivity"
    }
}

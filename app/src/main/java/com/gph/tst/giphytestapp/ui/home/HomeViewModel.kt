@file:OptIn(
    ExperimentalCoroutinesApi::class, FlowPreview::class, ExperimentalCoroutinesApi::class,
    FlowPreview::class, FlowPreview::class
)

package com.gph.tst.giphytestapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.data.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val giphyRepository: com.gph.tst.giphytestapp.data.repository.GiphyRepository,
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    val giphyFlow = queryFlow.asStateFlow()
        .debounce(500)
        .flatMapLatest {
            giphyRepository.getPagedGifs(it)
        }
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.filter { !it.removed }
        }

    fun setQuery(query: String) {
        queryFlow.value = query
    }

    fun remove(giphyLocalEntity: com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity) {
        viewModelScope.launch {
            giphyRepository.remove(giphyLocalEntity)
        }
    }
}
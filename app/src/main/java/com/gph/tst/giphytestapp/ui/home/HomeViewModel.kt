@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.gph.tst.giphytestapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gph.tst.giphytestapp.data.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val giphyRepository: GiphyRepository,
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    val giphyFlow = queryFlow.asStateFlow()
        .debounce(500)
        .flatMapLatest {
            giphyRepository.getPagedGifs(it)
        }
        .cachedIn(viewModelScope)

    fun setQuery(query: String) {
        queryFlow.value = query
    }
}
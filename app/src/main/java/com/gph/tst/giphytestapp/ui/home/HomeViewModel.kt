@file:OptIn(
    ExperimentalCoroutinesApi::class, FlowPreview::class, ExperimentalCoroutinesApi::class,
    FlowPreview::class, FlowPreview::class
)

package com.gph.tst.giphytestapp.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.data.repository.GiphyRepository
import com.gph.tst.giphytestapp.data.repository.RemoteFavouriteRepository
import com.gph.tst.giphytestapp.domain.entity.GifModel
import com.gph.tst.giphytestapp.domain.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val giphyRepository: GiphyRepository,
    private val favouriteRepository: FavouriteRepository,
) : ViewModel() {

    val query = savedStateHandle.getStateFlow("query", "")

    val giphyFlow = query
        .debounce(500)
        .flatMapLatest {
            giphyRepository.getPagedGifs(it)
        }
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.filter { !it.removed }
        }

    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }

    fun remove(giphyLocalEntity: GiphyLocalEntity) {
        viewModelScope.launch {
            giphyRepository.remove(giphyLocalEntity)
        }
    }

    fun save(giphyLocalEntity: GiphyLocalEntity) {
        viewModelScope.launch {
            favouriteRepository.save(
                gifModel = GifModel(
                    id = giphyLocalEntity.id,
                    title = giphyLocalEntity.title,
                    url = giphyLocalEntity.url
                )
            )
        }
    }
}
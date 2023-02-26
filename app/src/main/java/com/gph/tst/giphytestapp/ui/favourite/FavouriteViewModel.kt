package com.gph.tst.giphytestapp.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gph.tst.giphytestapp.domain.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
) : ViewModel() {

    val gifStateFlow = favouriteRepository.fetchAll()

    fun remove(id: String) {
        viewModelScope.launch {
            favouriteRepository.remove(id)
        }
    }

}
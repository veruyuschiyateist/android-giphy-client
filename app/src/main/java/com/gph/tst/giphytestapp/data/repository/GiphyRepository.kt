package com.gph.tst.giphytestapp.data.repository

import androidx.paging.PagingData
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.data.network.model.GifData
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {

    /**
     * Get the paging list of gifs.
     */
    fun getPagedGifs(query: String = ""): Flow<PagingData<GiphyLocalEntity>>
}
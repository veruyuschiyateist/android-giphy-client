package com.gph.tst.giphytestapp.data.repository

import androidx.paging.PagingData
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {

    fun getPagedGifs(query: String = ""): Flow<PagingData<GiphyLocalEntity>>

    suspend fun remove(id: String)

    /**
     * Deprecated
     */
    suspend fun remove(giphyLocalEntity: GiphyLocalEntity)
}
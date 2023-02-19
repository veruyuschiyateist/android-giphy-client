package com.gph.tst.giphytestapp.data.repository

import androidx.paging.PagingData
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {

    /**
     * Local model [GiphyLocalEntity] is emitted for simplicity
     */
    fun getPagedGifs(query: String = ""): Flow<PagingData<GiphyLocalEntity>>

    suspend fun remove(id: String)


    @Deprecated(message = "Will be removed in the future")
    suspend fun remove(giphyLocalEntity: GiphyLocalEntity)
}
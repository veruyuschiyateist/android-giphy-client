@file:OptIn(ExperimentalPagingApi::class)

package com.gph.tst.giphytestapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.gph.tst.giphytestapp.data.local.dao.GiphyDao
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.data.network.api.GiphyApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GiphyRemoteMediator @AssistedInject constructor(
    private val giphyApi: GiphyApi,
    private val giphyDao: GiphyDao,
    @Assisted private val query: String,
) : RemoteMediator<Int, GiphyLocalEntity>() {

    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GiphyLocalEntity>,
    ): MediatorResult {
        pageIndex =
            adjustPageIndex(loadType)
                ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val gifs = fetchGifs(limit, offset)

            giphyDao.insert(gifs)

            MediatorResult.Success(
                endOfPaginationReached = gifs.size < limit
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun adjustPageIndex(loadType: LoadType): Int? = when (loadType) {
        LoadType.REFRESH -> 0
        LoadType.PREPEND -> null
        LoadType.APPEND -> ++pageIndex
    }

    private suspend fun fetchGifs(limit: Int, offset: Int): List<GiphyLocalEntity> {
        val response = giphyApi.search(
            query = query,
            limit = limit,
            offset = offset
        )

        return emptyList()
    }

    @AssistedFactory
    interface Factory {
        fun create(query: String): GiphyRemoteMediator
    }

}
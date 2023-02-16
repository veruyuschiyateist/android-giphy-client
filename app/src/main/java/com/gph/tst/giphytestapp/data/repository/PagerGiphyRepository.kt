@file:OptIn(ExperimentalPagingApi::class)

package com.gph.tst.giphytestapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gph.tst.giphytestapp.data.GiphyRemoteMediator
import com.gph.tst.giphytestapp.data.local.dao.GiphyDao
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.data.network.api.GiphyApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagerGiphyRepository @Inject constructor(
    private val giphyDao: GiphyDao,
    private val giphyRemoteMediator: GiphyRemoteMediator.Factory,
) : GiphyRepository {

    override fun getPagedGifs(query: String): Flow<PagingData<GiphyLocalEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE
            ),
            remoteMediator = giphyRemoteMediator.create(query),
            pagingSourceFactory = {
                giphyDao.getGiphyPagingSource(query)
            }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 8
    }
}
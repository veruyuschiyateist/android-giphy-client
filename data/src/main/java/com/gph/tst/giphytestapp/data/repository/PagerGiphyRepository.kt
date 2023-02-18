@file:OptIn(ExperimentalPagingApi::class)

package com.gph.tst.giphytestapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gph.tst.giphytestapp.data.GiphyRemoteMediator
import com.gph.tst.giphytestapp.data.local.dao.GiphyDao
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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
                giphyDao.getPagingSource(query)
            }
        ).flow
    }

    override suspend fun remove(id: String) = withContext(Dispatchers.IO) {
        val removed = giphyDao.getById(id)
        if (removed != null) {
            giphyDao.update(removed.copy(removed = true))
        }
    }

    override suspend fun remove(giphyLocalEntity: GiphyLocalEntity) = withContext(Dispatchers.IO) {
        val removed = giphyLocalEntity.copy(
            removed = true
        )

        giphyDao.update(removed)
    }

    companion object {
        const val PAGE_SIZE = 8
    }
}
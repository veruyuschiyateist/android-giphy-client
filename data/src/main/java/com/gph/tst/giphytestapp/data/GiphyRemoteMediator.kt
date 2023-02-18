@file:OptIn(ExperimentalPagingApi::class)

package com.gph.tst.giphytestapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.gph.tst.giphytestapp.data.local.dao.GiphyDao
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.data.mappers.toLocalEntity
import com.gph.tst.giphytestapp.data.network.api.GiphyApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

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
            getPageIndex(loadType)
                ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val gifs = fetchGifs(limit, offset)
            if (gifs.isNotEmpty()) {
                giphyDao.insertAll(gifs)
            }

            MediatorResult.Success(
                endOfPaginationReached = gifs.size < limit
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? = when (loadType) {
        LoadType.REFRESH -> 0
        LoadType.PREPEND -> null
        LoadType.APPEND -> ++pageIndex
    }

    private suspend fun fetchGifs(limit: Int, offset: Int): List<GiphyLocalEntity> {
        val response = withContext(Dispatchers.IO) {
            giphyApi.search(
                query = query,
                limit = limit,
                offset = offset
            )
        }

        if (response.isSuccessful) {
            return response.body()!!.data
                .map { it.toLocalEntity() }

        } else {
            throw Exception()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(query: String): GiphyRemoteMediator
    }

}
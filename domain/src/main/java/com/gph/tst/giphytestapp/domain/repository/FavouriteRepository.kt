package com.gph.tst.giphytestapp.domain.repository

import com.gph.tst.giphytestapp.common.Resource
import com.gph.tst.giphytestapp.domain.entity.GifModel
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    fun fetchAll(): Flow<List<GifModel>>

    suspend fun remove(id: String)

    suspend fun save(gifModel: GifModel)
}
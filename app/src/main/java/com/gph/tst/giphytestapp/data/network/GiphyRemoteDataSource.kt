package com.gph.tst.giphytestapp.data.network

import com.gph.tst.giphytestapp.data.network.api.GiphyApi
import com.gph.tst.giphytestapp.data.network.model.GiphyApiModel
import retrofit2.Response
import javax.inject.Inject

class GiphyRemoteDataSource @Inject constructor(
    private val giphyApi: GiphyApi,
) {
    suspend fun search(
        query: String,
        limit: Int = 25,
        offset: Int = 0,
        rating: String = "pg",
        language: String = "en",
    ): Response<GiphyApiModel> {
        return giphyApi.search(query, limit, offset, rating, language)
    }
}
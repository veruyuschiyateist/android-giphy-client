package com.gph.tst.giphytestapp.data.network.model

import com.squareup.moshi.Json

data class GiphyApiResponse(
    @Json(name = "data") val data: List<GiphyApiModel>,
)

data class GiphyApiModel(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "images") val images: GiphyImagesApiModel,
)

data class GiphyImagesApiModel(
    @Json(name = "original") val original: GiphyOriginalApiModel,
)

data class GiphyOriginalApiModel(
    @Json(name = "url") val url: String,
    @Json(name = "height") val height: Int,
    @Json(name = "width") val width: Int,
)


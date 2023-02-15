package com.gph.tst.giphytestapp.data.network.model

data class GiphyApiModel(
    val `data`: List<GifData>,
    val meta: Meta,
    val pagination: Pagination
)
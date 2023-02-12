package com.gph.tst.giphytestapp.data.network.model

data class GiphyResponse(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)
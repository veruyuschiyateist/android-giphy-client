package com.gph.tst.giphytestapp.domain.entity

import java.io.Serializable

data class GifModel(
    val id: String = "",
    val title: String = "",
    val url: String = "",
) : Serializable

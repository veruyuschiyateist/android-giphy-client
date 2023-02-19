package com.gph.tst.giphytestapp.ui.carousel

import androidx.annotation.DrawableRes

data class ImageItem(
    @DrawableRes val id: Int,
    val width: Int = 0,
    val height: Int = 0,
) {

    val aspectRatio: Float
        get() = width / height.toFloat()
}

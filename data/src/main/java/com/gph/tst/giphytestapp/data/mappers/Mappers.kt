package com.gph.tst.giphytestapp.data.mappers

import com.google.firebase.auth.FirebaseUser
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity
import com.gph.tst.giphytestapp.data.network.model.GiphyApiModel
import com.gph.tst.giphytestapp.domain.entity.UserModel

/**
 * Converts [GiphyApiModel] network model to [GiphyLocalEntity] data model
 */
fun GiphyApiModel.toLocalEntity(): GiphyLocalEntity =
    GiphyLocalEntity(
        id = this.id,
        title = this.title,
        url = this.images.original.url,
        height = this.images.original.height,
        width = this.images.original.width
    )

fun GiphyLocalEntity.toUiEntity() = Unit

fun FirebaseUser.toDomain(): UserModel =
    UserModel(
        uid = this.uid,
        name = this.displayName,
        email = this.email
    )
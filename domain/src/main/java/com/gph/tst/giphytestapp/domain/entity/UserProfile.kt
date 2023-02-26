package com.gph.tst.giphytestapp.domain.entity

import java.io.Serializable

data class UserProfile(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val phone: String = ""
) : Serializable

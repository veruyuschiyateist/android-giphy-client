package com.gph.tst.giphytestapp.domain.repository

import com.gph.tst.giphytestapp.common.network.NetworkResult

interface AuthRepository {

    suspend fun signUp(email: String, password: String): NetworkResult<Unit>

    suspend fun signIn(email: String, password: String): NetworkResult<Unit>
}
package com.gph.tst.giphytestapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.gph.tst.giphytestapp.data.mappers.toDomain
import com.gph.tst.giphytestapp.domain.AuthState
import com.gph.tst.giphytestapp.domain.entity.UserModel
import com.gph.tst.giphytestapp.domain.repository.AuthManager
import javax.inject.Inject

class FirebaseAuthManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthManager {

    override val currentUser: UserModel?
        get() = firebaseAuth.currentUser?.toDomain()

    override val getAuthState: AuthState
        get() = if (currentUser == null) AuthState.NOT_AUTHENTICATED else AuthState.AUTHENTICATED
}
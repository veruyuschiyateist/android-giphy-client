package com.gph.tst.giphytestapp.domain.repository

import com.gph.tst.giphytestapp.domain.AuthState
import com.gph.tst.giphytestapp.domain.entity.UserModel

interface AuthManager {

    /**
     * Return current user if authenticated else null
     */
    val currentUser: UserModel?

    val getAuthState: AuthState
}
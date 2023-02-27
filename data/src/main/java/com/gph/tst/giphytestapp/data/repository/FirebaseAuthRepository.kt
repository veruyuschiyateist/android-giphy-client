package com.gph.tst.giphytestapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.gph.tst.giphytestapp.common.Resource
import com.gph.tst.giphytestapp.common.network.NetworkResult
import com.gph.tst.giphytestapp.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override suspend fun signUp(email: String, password: String): NetworkResult<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            Resource.Success(Unit)
        } catch (e: FirebaseAuthException) {
            Resource.Failure(cause = e)
        }
    }

    override suspend fun signIn(email: String, password: String): NetworkResult<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()

            Resource.Success(Unit)
        } catch (e: FirebaseAuthException) {
            Resource.Failure(cause = e)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

}
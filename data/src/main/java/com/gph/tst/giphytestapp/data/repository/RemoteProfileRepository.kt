package com.gph.tst.giphytestapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.gph.tst.giphytestapp.common.Resource
import com.gph.tst.giphytestapp.domain.entity.UserProfile
import com.gph.tst.giphytestapp.domain.repository.AuthManager
import com.gph.tst.giphytestapp.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteProfileRepository @Inject constructor(
    store: FirebaseFirestore,
    private val authManager: AuthManager,
) : ProfileRepository {

    private val collection = store.collection("profiles")

    /**
     * Coroutine Dispatcher here is hardcoded for simplicity
     */
    override suspend fun getUserProfile(): Resource<UserProfile, Exception> =
        withContext(Dispatchers.IO) {
            val userId: String =
                authManager.currentUser?.uid ?: return@withContext Resource.Failure(Exception())

            return@withContext try {
                val snapShot = collection.document(userId).get().await()
                val userProfile = snapShot.toObject<UserProfile>()
                if (userProfile == null) {
                    Resource.Failure(Exception())
                } else {
                    Resource.Success(userProfile)
                }
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }

    override suspend fun saveUserProfile(profile: UserProfile): Resource<Unit, Exception> =
        withContext(Dispatchers.IO) {
            val userId: String =
                authManager.currentUser?.uid ?: return@withContext Resource.Failure(Exception())

            val userMap = hashMapOf(
                "name" to profile.name,
                "surname" to profile.surname,
                "phone" to profile.phone
            )

            return@withContext try {
                collection.document(userId)
                    .set(userMap)
                    .await()

                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }
}
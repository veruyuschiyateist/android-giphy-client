package com.gph.tst.giphytestapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.gph.tst.giphytestapp.domain.entity.GifModel
import com.gph.tst.giphytestapp.domain.repository.AuthManager
import com.gph.tst.giphytestapp.domain.repository.FavouriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteFavouriteRepository @Inject constructor(
    store: FirebaseFirestore,
    private val authManager: AuthManager,
) : FavouriteRepository {

    private val collection = store.collection("favourites")

    override fun fetchAll(): Flow<List<GifModel>> {
        val userId: String =
            authManager.currentUser?.uid!!

        return collection
            .document(userId)
            .collection("Objects")
            .snapshots()
            .map { querySnapshot ->
                querySnapshot.toObjects(GifModel::class.java)
            }
    }

    override suspend fun remove(id: String) {
        withContext(Dispatchers.IO) {
            val userId: String = authManager.currentUser?.uid!!

            collection
                .document(userId)
                .collection("Objects")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (doc in task.result) {
                            collection
                                .document(userId)
                                .collection("Objects")
                                .document(doc.id)
                                .delete()
                        }
                    }
                }
        }
    }

    override suspend fun save(gifModel: GifModel) {
        val userId: String =
            authManager.currentUser?.uid!!

        val giphyMap = hashMapOf(
            "id" to gifModel.id,
            "title" to gifModel.title,
            "url" to gifModel.url
        )

        withContext(Dispatchers.IO) {
            collection
                .document(userId)
                .collection("Objects")
                .add(giphyMap)
                .await()
        }
    }
}

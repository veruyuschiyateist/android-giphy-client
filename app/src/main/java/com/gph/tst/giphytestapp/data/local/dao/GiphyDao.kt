package com.gph.tst.giphytestapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity

@Dao
interface GiphyDao {

    @Query("SELECT * FROM gifs WHERE title LIKE '%' || :query || '%' ")
    fun getGiphyPagingSource(
        query: String,
    ): PagingSource<Int, GiphyLocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gifs: List<GiphyLocalEntity>)

    @Query("DELETE FROM gifs WHERE id = :id")
    suspend fun delete(id: String)

    @Transaction
    suspend fun update(id: String, gifs: List<GiphyLocalEntity>) {
        delete(id)
        insert(gifs)
    }
}
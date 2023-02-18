package com.gph.tst.giphytestapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity

@Dao
interface GiphyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(gifs: List<GiphyLocalEntity>)


    @Query("SELECT * FROM gifs WHERE title LIKE '%' || :query || '%' ")
    fun getPagingSource(
        query: String,
    ): PagingSource<Int, GiphyLocalEntity>

    @Query("SELECT * FROM gifs WHERE id = :id")
    suspend fun getById(id: String): GiphyLocalEntity?



    @Transaction
    suspend fun update(query: String, gifs: List<GiphyLocalEntity>) {
        insertAll(gifs)
    }


    @Update
    suspend fun update(giphyLocalEntity: GiphyLocalEntity)

}
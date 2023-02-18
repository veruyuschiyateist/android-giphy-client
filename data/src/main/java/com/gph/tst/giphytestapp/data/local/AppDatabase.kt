package com.gph.tst.giphytestapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gph.tst.giphytestapp.data.local.dao.GiphyDao
import com.gph.tst.giphytestapp.data.local.entity.GiphyLocalEntity

@Database(entities = [GiphyLocalEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGiphyDao(): GiphyDao
}
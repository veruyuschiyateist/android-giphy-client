package com.gph.tst.giphytestapp.data.di

import android.content.Context
import androidx.room.Room
import com.gph.tst.giphytestapp.data.local.AppDatabase
import com.gph.tst.giphytestapp.data.local.dao.GiphyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideGiphyDao(appDatabase: AppDatabase): GiphyDao = appDatabase.getGiphyDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    private const val DATABASE_NAME = "giphy.db"
}
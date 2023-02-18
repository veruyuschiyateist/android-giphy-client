package com.gph.tst.giphytestapp.data.di

import com.gph.tst.giphytestapp.data.repository.GiphyRepository
import com.gph.tst.giphytestapp.data.repository.PagerGiphyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindGiphyRepository(pagerGiphyRepository: PagerGiphyRepository): GiphyRepository
}

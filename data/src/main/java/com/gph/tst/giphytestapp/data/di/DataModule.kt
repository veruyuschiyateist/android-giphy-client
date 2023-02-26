package com.gph.tst.giphytestapp.data.di

import com.gph.tst.giphytestapp.data.repository.*
import com.gph.tst.giphytestapp.domain.repository.AuthManager
import com.gph.tst.giphytestapp.domain.repository.AuthRepository
import com.gph.tst.giphytestapp.domain.repository.FavouriteRepository
import com.gph.tst.giphytestapp.domain.repository.ProfileRepository
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

    @Singleton
    @Binds
    abstract fun bindAuthRepository(firebaseAuthRepository: FirebaseAuthRepository): AuthRepository

    @Singleton
    @Binds
    abstract fun bindAuthManger(firebaseAuthManager: FirebaseAuthManager): AuthManager

    @Singleton
    @Binds
    abstract fun bindRemoteProfileRepository(remoteProfileRepository: RemoteProfileRepository): ProfileRepository

    @Singleton
    @Binds
    abstract fun bindFavouriteRepository(remoteFavouriteRepository: RemoteFavouriteRepository): FavouriteRepository
}

package com.pusatruq.muttabaah.di.module

import com.pusatruq.muttabaah.data.local.MenuLocalDataSource
import com.pusatruq.muttabaah.data.local.NewsLocalDataSource
import com.pusatruq.muttabaah.data.remote.MenuRemoteDataSource
import com.pusatruq.muttabaah.data.remote.NewsRemoteDataSource
import com.pusatruq.muttabaah.data.repository.MenuDataSource
import com.pusatruq.muttabaah.data.repository.NewsDataSource
import com.pusatruq.muttabaah.di.qualifier.LocalData
import com.pusatruq.muttabaah.di.qualifier.RemoteData
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    @LocalData
    abstract fun bindNewsLocalDataSource(newsLocalDataSource: NewsLocalDataSource): NewsDataSource

    @Singleton
    @Binds
    @RemoteData
    abstract fun bindNewsRemoteDataSource(newsRemoteDataSource: NewsRemoteDataSource): NewsDataSource

    @Singleton
    @Binds
    @LocalData
    abstract fun bindMenuLocalDataSource(menuLocalDataSource: MenuLocalDataSource): MenuDataSource

    @Singleton
    @Binds
    @RemoteData
    abstract fun bindMenuRemoteDataSource(menuRemoteDataSource: MenuRemoteDataSource): MenuDataSource
}
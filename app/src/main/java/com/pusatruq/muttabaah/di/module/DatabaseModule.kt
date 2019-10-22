package com.pusatruq.muttabaah.di.module

import androidx.room.Room
import com.pusatruq.muttabaah.BLApplication
import com.pusatruq.muttabaah.data.local.room.AppDatabase
import com.pusatruq.muttabaah.data.local.room.CommentDao
import com.pusatruq.muttabaah.data.local.room.NewsDao
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: BLApplication): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "mtb.db").build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(database: AppDatabase): NewsDao = database.newsDao()

    @Singleton
    @Provides
    fun provideCommentDao(database: AppDatabase): CommentDao = database.commentDao()

    @Singleton
    @Provides
    fun provideMenuDao(database: AppDatabase): MenuDao = database.menuDao()
}
package com.pusatruq.muttabaah.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuDao
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity

/**
 * Created by cuongpm on 12/1/18.
 */

@Database(entities = [NewsEntity::class, CommentEntity::class, MenuEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun commentDao(): CommentDao
    abstract fun menuDao(): MenuDao
}
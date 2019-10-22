package com.pusatruq.muttabaah.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by cuongpm on 12/1/18.
 */

@Dao
interface NewsDao {

    @Query("SELECT * FROM News ORDER BY time ASC")
    fun getAllNews(): Maybe<List<NewsEntity>>

    @Query("SELECT * FROM News WHERE id = :newsId")
    fun getNewsById(newsId: Int): Flowable<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsEntity)
}
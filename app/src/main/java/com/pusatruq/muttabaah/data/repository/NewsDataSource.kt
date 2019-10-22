package com.pusatruq.muttabaah.data.repository

import com.pusatruq.muttabaah.data.local.room.CommentEntity
import com.pusatruq.muttabaah.data.local.room.NewsEntity
import io.reactivex.Flowable

/**
 * Created by cuongpm on 11/29/18.
 */

interface NewsDataSource {

    fun getAllNews(): Flowable<List<NewsEntity>>

    fun getAllComments(newsId: Int): Flowable<List<CommentEntity>>

    fun saveAllNews(news: List<NewsEntity>)

    fun saveAllComments(comments: List<CommentEntity>)

    fun refreshNews()

    fun refreshComments()
}
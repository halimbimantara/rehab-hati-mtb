package com.pusatruq.muttabaah.data.local

import com.pusatruq.muttabaah.data.local.room.CommentDao
import com.pusatruq.muttabaah.data.local.room.CommentEntity
import com.pusatruq.muttabaah.data.local.room.NewsDao
import com.pusatruq.muttabaah.data.local.room.NewsEntity
import com.pusatruq.muttabaah.data.repository.NewsDataSource
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Singleton
class NewsLocalDataSource @Inject constructor(
    private val newsDao: NewsDao,
    private val commentDao: CommentDao
) : NewsDataSource {

    override fun getAllNews(): Flowable<List<NewsEntity>> {
        return newsDao.getAllNews().toFlowable()
    }

    override fun getAllComments(newsId: Int): Flowable<List<CommentEntity>> {
        return commentDao.getAllCommentByNewsId(newsId).toFlowable()
    }

    override fun saveAllNews(news: List<NewsEntity>) {
        news.map { newsDao.insertNews(it) }
    }

    override fun saveAllComments(comments: List<CommentEntity>) {
        comments.map { commentDao.insertComment(it) }
    }

    override fun refreshNews() {
    }

    override fun refreshComments() {
    }
}
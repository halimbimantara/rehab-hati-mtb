package com.pusatruq.muttabaah.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by cuongpm on 12/12/18.
 */

@Dao
interface CommentDao {

    @Query("SELECT * FROM Comment WHERE parent = :newsId ORDER BY time ASC")
    fun getAllCommentByNewsId(newsId: Int): Maybe<List<CommentEntity>>

    @Query("SELECT * FROM Comment WHERE id = :commentId")
    fun getCommentById(commentId: Int): Flowable<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: CommentEntity)
}
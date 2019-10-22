package com.pusatruq.muttabaah.data.repository

import com.pusatruq.muttabaah.data.local.room.CommentEntity
import com.pusatruq.muttabaah.data.local.room.NewsEntity
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by cuongpm on 11/29/18.
 */

interface MenuDataSource {

    fun getAllMenu(): Flowable<List<MenuEntity>>
//    fun getAllMenus(): JSONObject
    fun getMenu(): Flowable<List<MenuEntity>>
    fun getSubmenu(parentId: Int): Flowable<List<MenuEntity>>

    fun saveAllMenu(news: List<MenuEntity>)

    fun saveAllComments(comments: List<MenuEntity>)

    fun refreshNews()

    fun refreshComments()
}
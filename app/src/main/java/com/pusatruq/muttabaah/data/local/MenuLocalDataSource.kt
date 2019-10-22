package com.pusatruq.muttabaah.data.local

import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuDao
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity
import com.pusatruq.muttabaah.data.repository.MenuDataSource
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Singleton
class MenuLocalDataSource @Inject constructor(
    private val menuDao: MenuDao
) : MenuDataSource {
    override fun getAllMenu(): Flowable<List<MenuEntity>> {
        return menuDao.getAllMenu().toFlowable()
    }

    override fun getMenu(): Flowable<List<MenuEntity>> {
        return menuDao.getMenu().toFlowable()
    }

    override fun getSubmenu(parentId: Int): Flowable<List<MenuEntity>> {
        return menuDao.getSubMenuById(parentId).toFlowable()
    }

    override fun saveAllMenu(news: List<MenuEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveAllComments(comments: List<MenuEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshNews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshComments() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
package com.pusatruq.muttabaah.data.remote

import com.pusatruq.muttabaah.data.local.room.NewsEntity
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity
import com.pusatruq.muttabaah.data.repository.MenuDataSource
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by
 */

@Singleton
class MenuRemoteDataSource @Inject constructor(
    private val MenuService: MenuService
) : MenuDataSource {
    override fun saveAllMenu(news: List<MenuEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllMenu(): Flowable<List<MenuEntity>> {
        return MenuService.getAllmenu()
            .flatMap { menuItem ->
                Flowable.fromArray(menuItem).toList().toFlowable()
            }
    }

    override fun getMenu(): Flowable<List<MenuEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSubmenu(parentId: Int): Flowable<List<MenuEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveAllComments(comments: List<MenuEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshNews() {
    }

    override fun refreshComments() {
    }
}
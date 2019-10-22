package com.pusatruq.muttabaah.data.repository

import com.androidnetworking.AndroidNetworking
import com.pusatruq.muttabaah.data.AppEndPoint
import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity
import com.pusatruq.muttabaah.di.qualifier.LocalData
import com.pusatruq.muttabaah.di.qualifier.RemoteData
import io.reactivex.Flowable
import io.reactivex.Observable
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Singleton
class MenuRepository @Inject constructor(
    @LocalData private val localDataSource: MenuDataSource,
    @RemoteData private val remoteDataSource: MenuDataSource
) : MenuDataSource {
    override fun saveAllMenu(model: List<MenuEntity>) {
        localDataSource.saveAllMenu(model)
        remoteDataSource.saveAllMenu(model)
    }

    override fun getAllMenu(): Flowable<List<MenuEntity>> {
        if (cachedMenus.isNotEmpty() && !cacheMenuIsDirty) {
            return Flowable.just(cachedMenus)
        }
        val remoteNews = getAndSaveRemoteMenu()
        return if (cacheMenuIsDirty) remoteNews else {
            val localNews = getAndCacheLocalMenu()
            Flowable.concat(localNews, remoteNews)
        }
    }

//    override fun getAllMenus(): JSONObject {
//        AndroidNetworking.get(AppEndPoint.GET_ALL_MENU).build().getAsJSONObject()
//    }


    private fun getAndCacheLocalMenu(): Flowable<List<MenuEntity>> {
        return localDataSource.getAllMenu().doOnNext { news -> cachedMenus = news }
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

    private var cachedMenus = listOf<MenuEntity>()

    private var cacheMenuIsDirty = false


    private var cacheCommentsIsDirty = false

    override fun refreshNews() {
        cacheMenuIsDirty = true
    }

    override fun refreshComments() {
        cacheCommentsIsDirty = true
    }

    private fun getAndSaveRemoteMenu(): Flowable<List<MenuEntity>> {
        return remoteDataSource.getAllMenu()
            .doOnNext { news ->
                localDataSource.saveAllMenu(news)
                cachedMenus = news
            }.doOnComplete {
                cacheMenuIsDirty = false
            }
    }
}
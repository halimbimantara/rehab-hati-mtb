package com.pusatruq.muttabaah.data.remote

import com.pusatruq.muttabaah.data.local.room.muttabaah.MenuEntity
import io.reactivex.Flowable
import retrofit2.http.GET


/**
 * Customized by Mhbx [comrade]
 */
interface MenuService {
    @GET("allmenu")
    fun getAllmenu(): Flowable<MenuEntity>

}
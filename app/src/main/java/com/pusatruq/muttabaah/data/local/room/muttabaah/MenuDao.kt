package com.pusatruq.muttabaah.data.local.room.muttabaah

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
interface MenuDao {

    @Query("SELECT * FROM Menu")
    fun getAllMenu(): Maybe<List<MenuEntity>>

    @Query("SELECT * FROM Menu WHERE parent=0")
    fun getMenu(): Maybe<List<MenuEntity>>

    @Query("SELECT * FROM Menu WHERE parent = :menuId")
    fun getSubMenuById(menuId: Int): Maybe<List<MenuEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenu(news: MenuEntity)
}
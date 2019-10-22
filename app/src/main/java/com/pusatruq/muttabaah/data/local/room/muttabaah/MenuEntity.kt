package com.pusatruq.muttabaah.data.local.room.muttabaah

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pusatruq.muttabaah.util.RoomConverter
import com.pusatruq.muttabaah.util.getRelativeTime

/**
 * Created by cuongpm on 12/1/18.
 */

@Entity(tableName = "Menu")
data class MenuEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "id_kategori")
    @SerializedName("id_kategori")
    @Expose
    var idKategori: Int = 0,

    @ColumnInfo(name = "parent")
    @SerializedName("parent")
    @Expose
    var parent: Int = 0,

    @ColumnInfo(name = "nama")
    @SerializedName("nama")
    @Expose
    var title: String = ""

)
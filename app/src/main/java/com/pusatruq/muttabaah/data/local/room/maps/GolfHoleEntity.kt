package com.pusatruq.muttabaah.data.local.room.maps

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pusatruq.muttabaah.util.getRelativeTime

/**
 * Created by cuongpm on 12/12/18.
 */

@Entity(tableName = "Comment")
data class GolfHoleEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @ColumnInfo(name = "by")
    @SerializedName("by")
    @Expose
    var by: String = "",

    @ColumnInfo(name = "text")
    @SerializedName("text")
    @Expose
    var text: String = "",

    @ColumnInfo(name = "parent")
    @SerializedName("parent")
    @Expose
    var parent: Int = 0,

    @ColumnInfo(name = "time")
    @SerializedName("time")
    @Expose
    var time: Long = 0
) {

    var authorAndTime = ""
        get() = "${getRelativeTime(time)} by $by"

}
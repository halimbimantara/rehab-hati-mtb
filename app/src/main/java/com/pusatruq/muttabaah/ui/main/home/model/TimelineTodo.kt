package com.pusatruq.muttabaah.ui.main.home.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Panacea-Soft on 8/24/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


class TimelineTodo(@field:SerializedName("time")
                   var time: String, @field:SerializedName("interval")
                   var interval: String, @field:SerializedName("note")
                   var note: String, @field:SerializedName("color")
                   var color: String, @field:SerializedName("is_important")
                   var isImportant: Boolean?)

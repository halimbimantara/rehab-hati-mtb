package com.pusatruq.muttabaah.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getDateTimeNow(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
    }
}
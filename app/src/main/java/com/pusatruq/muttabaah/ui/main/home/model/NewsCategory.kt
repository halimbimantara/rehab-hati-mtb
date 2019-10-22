package com.pusatruq.muttabaah.ui.main.home.model

import com.google.gson.annotations.SerializedName


class NewsCategory(@field:SerializedName("category")
                   var category: String, @field:SerializedName("category_image")
                   var categoryImage: String, @field:SerializedName("is_check")
                   var isCheck: String)

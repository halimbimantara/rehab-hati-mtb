package com.pusatruq.muttabaah.ui.main.shop.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Panacea-Soft on 6/7/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


class ShopItem(@field:SerializedName("name")
               var name: String, @field:SerializedName("image")
               var imageName: String, @field:SerializedName("currency")
               var currency: String, @field:SerializedName("price")
               var price: String, @field:SerializedName("original_price")
               var originalPrice: String, @field:SerializedName("category_name")
               var categoryName: String, @field:SerializedName("rating_count")
               var ratingCount: String, @field:SerializedName("total_rating")
               var totalRating: String, @field:SerializedName("discount")
               var discount: String, @field:SerializedName("is_liked")
               var isLiked: Boolean?, @field:SerializedName("description")
               var description: String, imageList: List<Image>, shop: Shop, @field:SerializedName("view_count")
               var viewCount: String) {

    @SerializedName("image_list")
    var imageList: List<ShopItem.Image>

    @SerializedName("shop")
    var shop: ShopItem.Shop

    init {
        this.imageList = imageList
        this.shop = shop
    }

    inner class Image {
        @SerializedName("image_name")
        var imageName: String? = null
    }

    inner class Shop {
        @SerializedName("shop_name")
        var shopName: String? = null

        @SerializedName("shop_email")
        var shopEmail: String? = null

        @SerializedName("shop_phone")
        var shopPhone: String? = null

        @SerializedName("shop_website")
        var shopWebsite: String? = null

        @SerializedName("shop_address")
        var shopAddress: String? = null
    }
}

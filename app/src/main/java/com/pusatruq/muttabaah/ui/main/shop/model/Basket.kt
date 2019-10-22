package com.pusatruq.muttabaah.ui.main.shop.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Panacea-Soft on 6/28/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


class Basket(@field:SerializedName("name")
             var name: String, @field:SerializedName("image")
             var image: String, @field:SerializedName("currency")
             var currency: String, @field:SerializedName("price")
             var price: String, @field:SerializedName("category_name")
             var category_name: String, @field:SerializedName("color")
             var color: String, @field:SerializedName("size")
             var size: String, shop: Shop) {

    @SerializedName("shop")
    var shop: Basket.Shop

    init {
        this.shop = shop
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

package com.pusatruq.muttabaah.data.remote.shooping

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pusatruq.muttabaah.ui.main.shop.model.Basket

import java.util.ArrayList

/**
 * Created by Panacea-Soft on 6/28/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


object BasketItemRepository {

    val busketItemList: ArrayList<Basket>
        get() = Gson().fromJson<ArrayList<Basket>>(basketItems, object : TypeToken<ArrayList<Basket>>() {

        }.type)

    internal var basketItems = "[\n" +
            "  {\n" +
            "    \"id\":\"basket1\",\n" +
            "    \"name\":\"Unwind long sleeve tee\",\n" +
            "    \"image\":\"food_1\",\n" +
            "    \"currency\":\"Rp.\",\n" +
            "    \"price\":\"30000\",\n" +
            "    \"category_name\":\"Food\",\n" +
            "    \"color\":\"Red\",\n" +
            "    \"size\":\"Medium\",\n" +
            "    \"shop\" : {\n" +
            "      \"shop_name\":\"Manggo CAke\",\n" +
            "      \"shop_email\":\"teamps.is.cool@gmail.com\",\n" +
            "      \"shop_phone\":\"+957777777\",\n" +
            "      \"shop_website\":\"www.panacea-soft.com\",\n" +
            "      \"shop_address\":\"Alkida'a Street , Ghamdhan balding, First floor\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\":\"basket2\",\n" +
            "    \"name\":\"Elephant Tee\",\n" +
            "    \"image\":\"food_2\",\n" +
            "    \"currency\":\"Rp.\",\n" +
            "    \"price\":\"25000\",\n" +
            "    \"category_name\":\"Food\",\n" +
            "    \"color\":\"Green\",\n" +
            "    \"size\":\"Small\",\n" +
            "    \"shop\" : {\n" +
            "      \"shop_name\":\"Pan Cake\",\n" +
            "      \"shop_email\":\"teamps.is.cool@gmail.com\",\n" +
            "      \"shop_phone\":\"+957777777\",\n" +
            "      \"shop_website\":\"www.panacea-soft.com\",\n" +
            "      \"shop_address\":\"Alkida'a Street , Ghamdhan balding, First floor\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\":\"basket3\",\n" +
            "    \"name\":\"Backpack bear long tee\",\n" +
            "    \"image\":\"food_4\",\n" +
            "    \"currency\":\"Rp.\",\n" +
            "    \"price\":\"50000\",\n" +
            "    \"category_name\":\"Clothing\",\n" +
            "    \"color\":\"Blue\",\n" +
            "    \"size\":\"Large\",\n" +
            "    \"shop\" : {\n" +
            "      \"shop_name\":\"Egg Roasted\",\n" +
            "      \"shop_email\":\"teamps.is.cool@gmail.com\",\n" +
            "      \"shop_phone\":\"+957777777\",\n" +
            "      \"shop_website\":\"www.panacea-soft.com\",\n" +
            "      \"shop_address\":\"Alkida'a Street , Ghamdhan balding, First floor\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\":\"basket4\",\n" +
            "    \"name\":\"Infant plushee bear hoodie\",\n" +
            "    \"image\":\"food_6\",\n" +
            "    \"currency\":\"Rp.\",\n" +
            "    \"price\":\"100000\",\n" +
            "    \"category_name\":\"Food\",\n" +
            "    \"color\":\"Pink\",\n" +
            "    \"size\":\"Small\",\n" +
            "    \"shop\" : {\n" +
            "      \"shop_name\":\"Breakfast One\",\n" +
            "      \"shop_email\":\"teamps.is.cool@gmail.com\",\n" +
            "      \"shop_phone\":\"+957777777\",\n" +
            "      \"shop_website\":\"www.panacea-soft.com\",\n" +
            "      \"shop_address\":\"Alkida'a Street , Ghamdhan balding, First floor\"\n" +
            "    }\n" +
            "  }\n" +
            "\n" +
            "]"
}

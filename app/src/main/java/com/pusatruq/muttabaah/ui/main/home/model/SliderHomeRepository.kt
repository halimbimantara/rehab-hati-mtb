package com.pusatruq.muttabaah.ui.main.home.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.util.ArrayList

/**
 * Created by Panacea-Soft on 9/8/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
object SliderHomeRepository {

    val news: News
        get() {

            val newsArrayList = Gson().fromJson<ArrayList<News>>(json, object : TypeToken<ArrayList<News>>() {

            }.type)
            return newsArrayList[0]

        }

    val newsList: ArrayList<News>
        get() = Gson().fromJson<ArrayList<News>>(json, object : TypeToken<ArrayList<News>>() {

        }.type)

    internal var json = "[\n" +
            "  {\n" +
            "\n" +
            "    \"title\" : \"Berani Berhijrah\",\n" +
            "    \"desc\"  : \"TShots Of The Year\",\n" +
            "    \"date\"  : \"Aug. 5, 2018\",\n" +
            "    \"category\" : \"Technology\",\n" +
            "    \"ago\"   : \"Yesterday\",\n" +
            "    \"news_image\" : \"bg_slider_2\",\n" +
            "    \"publisher\" : \"CNET\",\n" +
            "    \"publisher_image\" : \"cnet\",\n" +
            "    \"total_like\" : \"20.2 k\"\n" +
            "  },\n" +
            "\n" +
            "  {\n" +
            "\n" +
            "    \"title\" : \"Sesungguhnya manusia dalam keaadaan merugi,kecuali orang2 beriman\",\n" +
            "    \"desc\"  : \"Golf news Leaderboard.\",\n" +
            "    \"date\"  : \"Aug. 6, 2018\",\n" +
            "    \"category\" : \"Technology\",\n" +
            "    \"ago\"   : \"1 week ago\",\n" +
            "    \"news_image\" : \"bg_slider_3\",\n" +
            "    \"publisher\" : \"CNET\",\n" +
            "    \"publisher_image\" : \"cnet\",\n" +
            "    \"total_like\" : \"32.2 k\"\n" +
            "  },\n" +
            "\n" +
            "  {\n" +
            "\n" +
            "    \"title\" : \"A swing thought to help improve the quality of your bad shots\",\n" +
            "    \"desc\"  : \"A swing thought to help improve the quality of your bad shots.\",\n" +
            "    \"date\"  : \"Aug. 6, 2018\",\n" +
            "    \"category\" : \"Technology\",\n" +
            "    \"ago\"   : \"2 week ago\",\n" +
            "    \"news_image\" : \"bg_slider_1\",\n" +
            "    \"publisher\" : \"CNET\",\n" +
            "    \"publisher_image\" : \"cnet\",\n" +
            "    \"total_like\" : \"2.2 k\"\n" +
            "  }"+
            "\n" +
            "\n" +
            "]"

}

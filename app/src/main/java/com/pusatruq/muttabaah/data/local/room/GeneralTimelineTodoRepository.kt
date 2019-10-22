package com.pusatruq.muttabaah.data.local.room

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pusatruq.muttabaah.ui.main.home.model.TimelineTodo

/**
 * Created by Panacea-Soft on 8/24/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


object GeneralTimelineTodoRepository {
    val toDoList: List<TimelineTodo>?
        get() = Gson().fromJson<List<TimelineTodo>>(json, object : TypeToken<List<TimelineTodo>>() {

        }.type)

    private val json = "[\n" +
            "  {\n" +
            "    \"time\":\"08:00 PM\",\n" +
            "    \"interval\":\"27min\",\n" +
            "    \"note\":\"Call Daniel for meetup on friday.\",\n" +
            "    \"color\":\"blue\",\n" +
            "    \"is_important\":\"false\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"time\":\"09:00 PM\",\n" +
            "    \"interval\":\"1h27min\",\n" +
            "    \"note\":\"Go to the washing machine.\",\n" +
            "    \"color\":\"green\",\n" +
            "    \"is_important\":\"false\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"time\":\"10:00 PM\",\n" +
            "    \"interval\":\"2h27min\",\n" +
            "    \"note\":\"Call mon.\",\n" +
            "    \"color\":\"orange\",\n" +
            "    \"is_important\":\"true\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"time\":\"11:00 PM\",\n" +
            "    \"interval\":\"3h27min\",\n" +
            "    \"note\":\"Prepare Behance case study.\",\n" +
            "    \"color\":\"blue\",\n" +
            "    \"is_important\":\"false\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"time\":\"12:00 PM\",\n" +
            "    \"interval\":\"4h27min\",\n" +
            "    \"note\":\"Call John for meeting.\",\n" +
            "    \"color\":\"green\",\n" +
            "    \"is_important\":\"false\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"time\":\"13:00 PM\",\n" +
            "    \"interval\":\"5h27min\",\n" +
            "    \"note\":\"Call babysitter.\",\n" +
            "    \"color\":\"blue\",\n" +
            "    \"is_important\":\"false\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"time\":\"14:00 PM\",\n" +
            "    \"interval\":\"6h27min\",\n" +
            "    \"note\":\"Continue Behance case study.\",\n" +
            "    \"color\":\"orange\",\n" +
            "    \"is_important\":\"false\"\n" +
            "  }\n" +
            "]"
}

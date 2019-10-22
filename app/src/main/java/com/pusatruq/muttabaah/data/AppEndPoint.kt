package com.pusatruq.muttabaah.data

object AppEndPoint {
    const val BASE_URL = "http://pusatruqyah.com/api/"
    const val GET_ALL_MENU = BASE_URL + "allmenu"
    const val POST_CHECK_USER = BASE_URL + "checkinguser"
    const val GET_MAIN_MENU = BASE_URL + "menu"
    const val GET_DETAIL_MENU = BASE_URL + "detailMenu/{id}"
    const val POST_DAILY_SHOLAT_RWT = BASE_URL + "submitSholat"
    const val POST_DAILY_SHOLAT_RWT_CHECKING = BASE_URL + "checking/{parent}"
    const val POST_REGISTER = BASE_URL + "submitRegister"

}

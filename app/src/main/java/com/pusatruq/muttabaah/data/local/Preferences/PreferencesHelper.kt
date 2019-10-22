package com.pusatruq.muttabaah.data.local.Preferences

interface PreferencesHelper {
    fun getAccessToken(): String
    fun setAccessToken(accessToken: String)

    fun getUsername(): String
    fun setUsername(username: String)

    fun getEmail(): String
    fun setEmail(username: String)

    fun getPict(): String
    fun setPict(username: String)
}
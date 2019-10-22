package com.pusatruq.muttabaah.data.local.Preferences

class AppPreferencesDataStore :PreferencesHelper{
    override fun getAccessToken(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setAccessToken(accessToken: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsername(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setUsername(username: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEmail(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setEmail(username: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPict(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPict(username: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val PREFERENCES_UNAME = "username"
        const val PREFERENCES_ID_USER = "id_user"
        const val PREFERENCES_EMAIL = "email"
        const val PREFERENCES_PICT_PROFILE = "pict"
        const val PREFERENCES_PHONE = "phone"
        const val PREFERENCES_FULL_NAME = "fname"
        const val PREFERENCES_GENDER = "gender"
    }

}
package com.pusatruq.muttabaah.ui.main.accounts.model

import com.google.gson.annotations.SerializedName

data class RegisterPost(
    @field:SerializedName("id_user")
    var IdUser: Int,
    @field:SerializedName("uname")
    var UserName: String,
    @field:SerializedName("gender")
    var Gender: String,
    @field:SerializedName("email")
    var Email: String,
    @field:SerializedName("fname")
    var FullName: String,
    @field:SerializedName("address")
    var address: String,
    @field:SerializedName("city")
    var City: String,
    @field:SerializedName("no_hp")
    var No_hp: String
)
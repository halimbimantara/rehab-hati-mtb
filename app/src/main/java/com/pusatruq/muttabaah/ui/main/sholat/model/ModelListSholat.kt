package com.pusatruq.muttabaah.ui.main.sholat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

 class ModelListSholat:Serializable {
    @Expose
    @SerializedName("result")
    var result: List<Result>? = null
    @Expose
    @SerializedName("status")
    var status: Boolean = false
    @Expose
    @SerializedName("status_code")
    var status_code: Int = 0

    class Result {
        @Expose
        @SerializedName("keterangan")
        var keterangan: String? = null
        @Expose
        @SerializedName("presentase")
        var presentase: String? = null
        @Expose
        @SerializedName("status")
        var status: String? = null
        @Expose
        @SerializedName("user_id")
        var user_id: String? = null
        @Expose
        @SerializedName("created_date")
        var created_date: String? = null
        @Expose
        @SerializedName("kategori")
        var kategori: String? = null
        @Expose
        @SerializedName("id_report")
        var id_report: String? = null
        @Expose
        @SerializedName("nama")
        var nama: String? = null
        @Expose
        @SerializedName("parent")
        var parent: String? = null
        @Expose
        @SerializedName("id_kategori")
        var id_kategori: String? = null
    }
}

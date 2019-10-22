package com.pusatruq.muttabaah.ui.main.scorring.model

import com.google.gson.annotations.SerializedName

data class ScoreTop(
    @field:SerializedName("id")
    var Id: Int, @field:SerializedName("hole_number")
    var holeNumber: String, @field:SerializedName("value")
    var holeValue: Int, @field:SerializedName("max_score")
    var holeMaxScore: Int
)
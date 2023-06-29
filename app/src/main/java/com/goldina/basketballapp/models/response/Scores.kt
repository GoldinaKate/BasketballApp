package com.goldina.basketballapp.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Scores(
    @SerializedName("1stQuarter")
    val firstQuarter: List<Quarter>,
    @SerializedName("2ndQuarter")
    val secondQuarter: List<Quarter>,
    @SerializedName("3rdQuarter")
    val thirdQuarter: List<Quarter>,
    @SerializedName("4thQuarter")
    val fourthQuarter: List<Quarter>
):Serializable
package com.goldina.basketballapp.models.response

import java.io.Serializable

data class Quarter(
    val score_away: Int,
    val score_home: Int
):Serializable
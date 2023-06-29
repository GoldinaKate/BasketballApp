package com.goldina.basketballapp.models.response

import java.io.Serializable

data class ResponseFixture(
    val success: Int,
    val result: List<Match>
):Serializable

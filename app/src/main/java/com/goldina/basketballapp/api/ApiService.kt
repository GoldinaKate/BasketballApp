package com.goldina.basketballapp.api

import com.goldina.basketballapp.models.response.ResponseFixture
import com.goldina.basketballapp.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constant.PLAY_NAME)
    suspend fun getFixture(
        @Query("met") met:String,
        @Query("APIkey") APIkey:String,
        @Query("from") from:String,
        @Query("to") to:String
    ): Response<ResponseFixture>


}
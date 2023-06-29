package com.goldina.basketballapp.utils

import androidx.room.TypeConverter
import com.goldina.basketballapp.models.response.Scores
import com.google.gson.Gson

class ScoreConverter {
    @TypeConverter
    fun fromScore(scores: Scores):String{
       return Gson().toJson(scores)
    }
    @TypeConverter
    fun toScore(data:String):Scores{
        return Gson().fromJson(data,Scores::class.java)
    }
}
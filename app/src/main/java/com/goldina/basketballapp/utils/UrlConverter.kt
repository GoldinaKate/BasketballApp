package com.goldina.basketballapp.utils

import androidx.room.TypeConverter

class UrlConverter {
    @TypeConverter
    fun fromUrl(url:String?): String {
        return url ?: ""
    }
}
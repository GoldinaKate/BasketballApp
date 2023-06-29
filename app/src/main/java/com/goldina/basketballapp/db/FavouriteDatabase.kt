package com.goldina.basketballapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.goldina.basketballapp.di.ApplicationScope
import com.goldina.basketballapp.models.response.Match
import com.goldina.basketballapp.utils.ScoreConverter
import com.goldina.basketballapp.utils.UrlConverter
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@TypeConverters(ScoreConverter::class,UrlConverter::class)
@Database(entities = [Match::class], version = 1)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun getFavMatches(): FavouriteDAO

    class Callback @Inject constructor(
        private val database: Provider<FavouriteDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}
package com.goldina.basketballapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goldina.basketballapp.models.response.Match

@Dao
interface FavouriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavMatch(match: Match)

    @Query("SELECT * FROM fav_match")
    suspend fun getFavMatches(): List<Match>

    @Query("DELETE FROM fav_match WHERE event_key = :eventKey")
    suspend fun deleteMatch(eventKey:Int)

    @Query("SELECT count(*) FROM fav_match WHERE event_key=:eventKey")
    suspend fun isFavourite(eventKey: Int):Int

}
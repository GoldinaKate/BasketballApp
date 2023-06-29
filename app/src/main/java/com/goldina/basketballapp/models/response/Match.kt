package com.goldina.basketballapp.models.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.goldina.basketballapp.utils.ScoreConverter
import com.goldina.basketballapp.utils.UrlConverter
import java.io.Serializable

@Entity(tableName = "fav_match")
data class Match(
    val away_team_key: Int,
    val country_name: String,
    val event_away_team: String,
    @TypeConverters(UrlConverter::class)
    val event_away_team_logo: String?,
    val event_date: String,
    val event_final_result: String,
    val event_home_team: String,
    @TypeConverters(UrlConverter::class)
    val event_home_team_logo: String?,
    @PrimaryKey(autoGenerate = false)
    val event_key: Int,
    val event_live: String,
    val event_status: String,
    val event_time: String,
    val home_team_key: Int,
    val league_key: Int,
    val league_name: String,
    val league_season: String,
    @TypeConverters(ScoreConverter::class)
    val scores: Scores,
):Serializable{
    override fun hashCode(): Int {
        var result = event_key.hashCode()
        if(event_home_team_logo.isNullOrEmpty()){
            result = 31 * result + "".hashCode()
        }
        if(event_away_team_logo.isNullOrEmpty()){
            result = 31 * result + "".hashCode()
        }

        return result
    }
}
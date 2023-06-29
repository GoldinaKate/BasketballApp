package com.goldina.basketballapp.repository

import com.goldina.basketballapp.api.ApiService
import com.goldina.basketballapp.db.FavouriteDAO
import com.goldina.basketballapp.models.response.Match
import com.goldina.basketballapp.models.response.ResponseFixture
import com.goldina.basketballapp.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FixtureRepository
@Inject constructor(private val apiService: ApiService,
                    private val favouriteDAO: FavouriteDAO){

    suspend fun getFixture(met:String,APIkey:String,from:String,to:String): Flow<DataState<ResponseFixture>> = flow {
        DataState.Loading
        try {
            val response = apiService.getFixture(met, APIkey, from, to)
            if(response.isSuccessful){
                emit(DataState.Success(response.body()))
            }else{
                emit(DataState.Error(response.message()))
            }
        }catch (ex:Exception){
            emit(DataState.Exception(ex))
        }
    }


    suspend fun getFavMatches() = favouriteDAO.getFavMatches()

    suspend fun addFavMatch(match: Match) = favouriteDAO.addFavMatch(match)

    suspend fun deleteMatch(eventKey: Int) = favouriteDAO.deleteMatch(eventKey)

    suspend fun isFavourite(eventKey: Int) = favouriteDAO.isFavourite(eventKey)
}
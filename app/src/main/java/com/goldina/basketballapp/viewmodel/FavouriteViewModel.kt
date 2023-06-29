package com.goldina.basketballapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldina.basketballapp.models.response.Match
import com.goldina.basketballapp.repository.FixtureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favRepository: FixtureRepository
    ) : ViewModel() {

    var listFavMatches = MutableLiveData<List<Match>>()

    fun updateList(){
        viewModelScope.launch {
            listFavMatches.value = favRepository.getFavMatches()
        }
    }

    fun addMatch(match: Match) {
        viewModelScope.launch {
            favRepository.addFavMatch(match)
            updateList()
        }
    }


    fun deleteMatch(eventKey: Int) {
        viewModelScope.launch {
            favRepository.deleteMatch(eventKey)
            updateList()
        }
    }
    suspend fun isFavourite(eventKey: Int) = favRepository.isFavourite(eventKey)
}
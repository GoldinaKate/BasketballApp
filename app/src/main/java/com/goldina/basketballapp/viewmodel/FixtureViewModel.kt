package com.goldina.basketballapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldina.basketballapp.models.response.ResponseFixture
import com.goldina.basketballapp.repository.FixtureRepository
import com.goldina.basketballapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixtureViewModel
@Inject
constructor(private val repository: FixtureRepository) : ViewModel() {

    private val _response = MutableLiveData<DataState<ResponseFixture>>()
    val fixtureResponse: LiveData<DataState<ResponseFixture>>
        get() = _response


    fun getFixture(met:String,APIkey:String,from:String,to:String) = viewModelScope.launch {
        repository.getFixture(met, APIkey, from, to).onEach { response ->
                _response.value = response
        }.launchIn(viewModelScope)
    }




}
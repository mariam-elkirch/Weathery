package com.example.weathery.alarm.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.example.weathery.model.RepositoryInterface
import com.example.weathery.model.Utilitis
import com.example.weathery.model.Weather2
import kotlinx.coroutines.*

private const val TAG = "AllMoviesFeature"

class AlarmViewModel (iRepo: RepositoryInterface) : ViewModel() {

    private val _iRepo: RepositoryInterface = iRepo



    init {
        Log.i(TAG, "instance initializer: Creation of ViewModel")

    }
   // lat:String,long:String,units:String,language:String


    fun insertWeather(weather2: Weather2){
                viewModelScope.launch(Dispatchers.IO) {
               Log.i("TAG","Vie Model lat insert"+weather2.lat.toString())
                    _iRepo.insertWeather(weather2)
                }
    }
    fun localWeather(): LiveData<List<Weather2>> {
        return _iRepo.getWeather()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ViewModel Cleared")
    }
}
package com.example.weathery.db

import androidx.lifecycle.LiveData
import com.example.weathery.model.Alarm
import com.example.weathery.model.Favourite
import com.example.weathery.model.Weather2


interface LocalSource {
    suspend fun insertFav(fav:Favourite)
    suspend fun deleteFav(fav: Favourite)
    fun getAllFavourite(): LiveData<List<Favourite>>
    suspend fun insertWeather(weather2: Weather2)
    suspend fun insertAlarm(alarm:Alarm)
    suspend fun deleteAlarm(alarm: Alarm)
    fun getAllAlarms(): LiveData<List<Alarm>>
    fun getWeather(): LiveData<List<Weather2>>
}
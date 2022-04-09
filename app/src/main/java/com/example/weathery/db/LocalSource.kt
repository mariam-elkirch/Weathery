package com.example.weathery.db

import androidx.lifecycle.LiveData
import com.example.weathery.model.Favourite
import com.example.weathery.model.Weather2


interface LocalSource {
    suspend fun insertFav(fav:Favourite)
    suspend fun deleteFav(fav: Favourite)
    fun getAllFavourite(): LiveData<List<Favourite>>
    suspend fun insertWeather(weather2: Weather2)
    fun getWeather(): LiveData<List<Weather2>>
}
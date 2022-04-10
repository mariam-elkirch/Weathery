package com.example.weathery.model

import androidx.lifecycle.LiveData
import retrofit2.Response
import java.util.ArrayList

interface RepositoryInterface {
    //suspend fun getFav(): Response<ArrayList<ResponceItem>>?
    suspend fun insertFav(item:Favourite)
    suspend fun deleteFav(item:Favourite)
    fun getAllFavourite(): LiveData<List<Favourite>>
    suspend fun insertAlarm(alarm: Alarm)
   suspend fun insertWeather(weather2: Weather2)
    fun getWeather(): LiveData<List<Weather2>>
    suspend fun getWearherNetwork(lat:String,long:String,units:String,language:String): Response<Weather2>
}
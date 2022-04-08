package com.example.weathery.network

import com.example.weathery.model.Weather2
import retrofit2.Response

interface RemoteSource {
    suspend fun getWeatherOverNetwork(lat:String,long:String,units:String,language:String): Response<Weather2>
}
package com.example.weathery.network

import com.example.weathery.model.Weather2
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.ArrayList


interface WebService {

    @GET("onecall?")
    suspend fun getWeather2(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("exclude")exclude:String?,
        @Query("appid")appid: String?,
        @Query("units")units: String?,
        @Query("lang")lang:String?
    ):Response<Weather2>

}
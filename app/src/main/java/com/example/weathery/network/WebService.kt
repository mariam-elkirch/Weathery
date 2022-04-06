package com.example.weathery.network

import com.example.weathery.model.Weather2
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {
  /*  @GET("weather?")
  suspend fun getWeather(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid")appid: String?

    ):Response<Weather>*/
    @GET("onecall?")
    suspend fun getWeather2(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("exclude")exclude:String?,
        @Query("appid")appid: String?

    ):Response<Weather2>
   /* @GET("everything")
    fun getNews(
        @Query("apiKey") key: String?,
        @Query("language") lang: String?,
        @Query("sources") sources: String?
    ):Response<Weather>
*/
}
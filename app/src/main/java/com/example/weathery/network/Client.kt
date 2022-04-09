package com.example.weathery.network

import com.example.weathery.model.Weather2
import retrofit2.Response

class Client  private constructor() : RemoteSource {
  /*  override suspend fun getWeatherOverNetwork(): List<Weather2> {
        val movieService = RetrofitHelper.getInstance().create(MovieService::class.java)
        val response = movieService.getAllMovies()
        return response
    }*/

    companion object{
        private var instance:Client? = null
        fun getInstance(): Client{
            return  instance?: Client()
        }
    }

    override suspend fun getWeatherOverNetwork(lat:String,long:String,units:String,language:String): Response<Weather2> {
        val weatherApi= ApiManager.getClient()!!.create(WebService ::class.java)
       // val responce= sportApi?.getWeather2("24.661994","5.167022","","272e72149cbb28b619e0d0a924024a41","metric","ar")
        return weatherApi.getWeather2(lat,long,"minutely","272e72149cbb28b619e0d0a924024a41",units,language)!!
    }
}
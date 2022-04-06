package com.example.weathery.network

import com.example.weathery.model.Weather2

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
}
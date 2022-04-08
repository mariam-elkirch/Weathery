package com.example.weathery.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    var context: Context? = null


    fun ApiManager() {
        this.context = context
    }
    companion object {
        var retrofit: Retrofit? = null
        var base_url ="https://api.openweathermap.org/data/2.5/"
        var client: ApiManager? = null
        fun getInstance(): ApiManager? {
            if (client == null) client = ApiManager()
            return client
        }
        fun getClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
    }
}
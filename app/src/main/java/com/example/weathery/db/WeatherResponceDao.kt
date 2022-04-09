package com.example.weathery.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathery.model.Favourite
import com.example.weathery.model.Weather2

@Dao
interface WeatherResponceDao {
    @Query("SELECT * From weather")
    fun getWeather(): LiveData<List<Weather2>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeather(weather2: Weather2)
}
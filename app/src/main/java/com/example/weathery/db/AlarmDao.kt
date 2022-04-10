package com.example.weathery.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weathery.model.Alarm
import com.example.weathery.model.Favourite

@Dao
interface AlarmDao {
    @Query("SELECT * From alarm")
    fun getAllAlarms(): LiveData<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)
}
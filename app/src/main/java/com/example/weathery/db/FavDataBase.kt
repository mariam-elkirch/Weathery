package com.example.mvvmkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weathery.db.WeatherDao

import com.example.weathery.model.Favourite

@Database(entities = [Favourite::class], version =14)
open abstract class FavDataBase: RoomDatabase() {
    abstract fun movieDao(): WeatherDao?
    companion object{
        private var instance: FavDataBase? = null
    @Synchronized
    open fun getInstance(context: Context): FavDataBase {

        return instance?:Room.databaseBuilder(
            context.applicationContext,
            FavDataBase::class.java, "weathery"
        ).fallbackToDestructiveMigration().build()

    }
}}
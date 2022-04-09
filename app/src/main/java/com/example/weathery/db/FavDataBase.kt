package com.example.mvvmkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weathery.db.WeatherDao
import com.example.weathery.db.WeatherResponceDao
import com.example.weathery.model.Converters

import com.example.weathery.model.Favourite
import com.example.weathery.model.Weather2

@Database(entities = [Favourite::class,Weather2::class], version =24)
@TypeConverters(Converters::class)
abstract class FavDataBase: RoomDatabase() {
    abstract fun movieDao(): WeatherDao?
    abstract fun weatherResDao(): WeatherResponceDao?

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
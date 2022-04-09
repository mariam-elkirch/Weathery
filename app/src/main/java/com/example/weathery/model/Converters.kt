package com.example.weathery.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromCurrentToString(current: Current)= Gson().toJson(current)
    @TypeConverter
    fun fromStringToCurrent(currentString:String)=Gson().fromJson(currentString,Current::class.java)

    @TypeConverter
    fun fromDailyToString(dailyItem:List<DailyItem>)=Gson().toJson(dailyItem)
    @TypeConverter
    fun fromStringToDaily(stringDaily:String)=Gson().fromJson(stringDaily,Array<DailyItem>::class.java).toList()

    @TypeConverter
    fun fromHourlyToString(hourlyItem:List<HourlyItem>)=Gson().toJson(hourlyItem)
    @TypeConverter
    fun fromStringToHourly(stringHourly:String)=Gson().fromJson(stringHourly,Array<HourlyItem>::class.java).toList()

    @TypeConverter
    fun fromAlertToString(alerts: List<Alerts>)=Gson().toJson(alerts)
    @TypeConverter
    fun fromStringToAlert(stringAlert:String)=Gson().fromJson(stringAlert,Array<Alerts>::class.java).toList()

}
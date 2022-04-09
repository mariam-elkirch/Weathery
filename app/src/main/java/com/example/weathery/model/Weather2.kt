package com.example.weathery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.jetbrains.annotations.Nullable

@Entity(tableName = "weather")
data class Weather2(
	@PrimaryKey
	var id:Int,
	var current: Current?,
	var timezone: String,
	var timezoneOffset: Int,
	var daily: List<DailyItem>?,
	var lon: Double,
	var hourly: List<HourlyItem>?,
	var lat: Double? = null,
	@Nullable
	@Ignore
	@ColumnInfo(defaultValue = "empty")
	var alerts: List<Alerts>?
){
	constructor() :this(0,null,"",0,null,0.0,null,0.0,null)
}
data class Alerts(
	var sender_name:String?=null,
	var event: String?=null,
	var start:Int?=null,
	var end:Int?=null,
	var description: String?=null,
	var tags:ArrayList<String> = arrayListOf(),
)
data class DailyItem(
	val moonset: Int? = null,
	val sunrise: Int? = null,
	val temp: Temp? = null,
	val moonPhase: Double? = null,
	val uvi: Double? = null,
	val moonrise: Int? = null,
	val pressure: Int? = null,
	val clouds: Int? = null,
	val feelsLike: FeelsLike? = null,
	val windGust: Double? = null,
	val dt: Int? = null,
	val pop: Double? = null,
	val windDeg: Int? = null,
	val dewPoint: Double? = null,
	val sunset: Int? = null,
	val weather: List<WeatherItem?>? = null,
	val humidity: Int? = null,
	val windSpeed: Double? = null,
	val rain: Double? = null
)

data class HourlyItem(
	val temp: Double? = null,
	val visibility: Int? = null,
	val uvi: Double? = null,
	val pressure: Int? = null,
	val clouds: Int? = null,
	val feelsLike: Double? = null,
	val windGust: Double? = null,
	val dt: Int? = null,
	val pop: Double? = null,
	val windDeg: Int? = null,
	val dewPoint: Double? = null,
	val weather: List<WeatherItem?>? = null,
	val humidity: Int? = null,
	val windSpeed: Double? = null
)

data class Temp(
	val min: Double? = null,
	val max: Double? = null,
	val eve: Double? = null,
	val night: Double? = null,
	val day: Double? = null,
	val morn: Double? = null
)

data class WeatherItem(
	val icon: String? = null,
	val description: String? = null,
	val main: String? = null,
	val id: Int? = null
)

data class MinutelyItem(
	val dt: Int? = null,
	val precipitation: Int? = null
)

data class FeelsLike(
	val eve: Double? = null,
	val night: Double? = null,
	val day: Double? = null,
	val morn: Double? = null
)

data class Current(
	val sunrise: Int? = null,
	val temp: Double? = null,
	val visibility: Int? = null,
	val uvi: Double? = null,
	val pressure: Int? = null,
	val clouds: Int? = null,
	val feelsLike: Double? = null,
	val dt: Int? = null,
	val windDeg: Int? = null,
	val dewPoint: Double? = null,
	val sunset: Int? = null,
	val weather: List<WeatherItem?>? = null,
	val humidity: Int? = null,
	val windSpeed: Double? = null
)


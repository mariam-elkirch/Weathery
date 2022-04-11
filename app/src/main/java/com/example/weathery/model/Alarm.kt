package com.example.weathery.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "alarm")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id:Int,
    @ColumnInfo(name = "startDate")
    var startDate: Long,
    @ColumnInfo(name = "endDate")
    var endDate:Long,
    @ColumnInfo(name = "time")
    var time:String,
    @ColumnInfo(name = "timeInMills")
    var timemills:Long,
    @ColumnInfo(name = "longitude")
    var longitude:String,
    @ColumnInfo(name = "latitude")
    var latitude:String
) {
    constructor() : this(0,1,1,"",1,"","")
}

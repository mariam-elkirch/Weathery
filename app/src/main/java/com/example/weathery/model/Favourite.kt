package com.example.weathery.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "location")
    var location: String,

    @ColumnInfo(name = "long")
    var long :String,

    @ColumnInfo(name = "lat")
    var lat :String,

    @ColumnInfo(name = "mlong")
    var mlong :String,

    ){
    constructor() :this("","","","")
}
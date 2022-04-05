package com.example.weathery.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav")
data class Favourite(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "location")
    var location: String="",

    @ColumnInfo(name = "lat")
    var lat :String="",

    @ColumnInfo(name = "long")
    var long :String=""
    )
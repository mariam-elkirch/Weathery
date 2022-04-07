package com.example.weathery.db

import androidx.lifecycle.LiveData
import com.example.weathery.model.Favourite


interface LocalSource {
    suspend fun insertFav(fav:Favourite)
    suspend fun deleteFav(fav: Favourite)
    fun getAllFavourite(): LiveData<List<Favourite>>
}
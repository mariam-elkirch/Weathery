package com.example.weathery.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weathery.model.Favourite


@Dao
interface WeatherDao {
    @Query("SELECT * From fav")
    fun getAllFavourite(): LiveData<List<Favourite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertAllFav(fav:Favourite)

    @Delete
  suspend fun deleteFav(fav: Favourite)
}

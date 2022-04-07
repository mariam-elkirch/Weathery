package com.example.weathery.db

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mvvmkotlin.db.FavDataBase
import com.example.weathery.model.Favourite


class  ConcreteLocalSource (context: Context) : LocalSource {
    private var dao: WeatherDao? = null
    var favourites: LiveData<List<Favourite>>

    init {
        val db: FavDataBase? = FavDataBase.getInstance(context!!.applicationContext)
        dao = db?.movieDao()
     favourites = dao!!.getAllFavourite()
    }

    companion object{
        private var localSource: ConcreteLocalSource? = null
    fun getInstance(context: Context): ConcreteLocalSource? {
        if (localSource == null) localSource = ConcreteLocalSource(context)
        return localSource
    }}

    override suspend fun insertFav(fav: Favourite) {
        Log.i("TAG","concrete local source")

        dao?.insertAllFav(fav)
    }

    override suspend fun deleteFav(fav: Favourite) {
        dao?.deleteFav(fav)
    }

    override  fun getAllFavourite(): LiveData<List<Favourite>> {
        return  favourites
    }

}
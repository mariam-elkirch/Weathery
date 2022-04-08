package com.example.weathery.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weathery.db.LocalSource
import com.example.weathery.network.RemoteSource
import retrofit2.Response

class Repository private constructor(
    var remoteSource: RemoteSource,
    var localSource: LocalSource,
    var context: Context
) : RepositoryInterface {

    companion object{
        private var instance: Repository? = null
        fun getInstance(remoteSource: RemoteSource,
                        localSource: LocalSource,
                        context: Context
        ): Repository{
            return instance?: Repository(
                remoteSource, localSource, context)
        }
    }

    override suspend fun insertFav(item: Favourite) {
        Log.i("TAG",item.long+"repo")
      localSource.insertFav(item)
    }

    override suspend fun deleteFav(item: Favourite) {
        TODO("Not yet implemented")
    }

    override fun getAllFavourite(): LiveData<List<Favourite>> {
      return  localSource.getAllFavourite()
    }

    override suspend fun getWearherNetwork(lat:String,long:String,units:String,language:String): Response<Weather2> {
        return remoteSource.getWeatherOverNetwork(lat,long,units,language)
    }
}
package com.example.weathery.model

import androidx.lifecycle.LiveData
import retrofit2.Response
import java.util.ArrayList

interface RepositoryInterface {
    //suspend fun getFav(): Response<ArrayList<ResponceItem>>?
    suspend fun insertFav(item:Favourite)
    suspend fun deleteFav(item:Favourite)
    fun getAllFavourite(): LiveData<List<Favourite>>
    suspend fun getWearherNetwork(lat:String,long:String,units:String,language:String): Response<Weather2>
}
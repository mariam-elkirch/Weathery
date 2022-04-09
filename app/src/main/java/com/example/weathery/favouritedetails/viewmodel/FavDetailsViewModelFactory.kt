package com.example.weathery.favouritedetails.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathery.model.RepositoryInterface
import java.lang.IllegalArgumentException

class FavDetailsViewModelFactory(val long:String,val lat:String,private val _irepo: RepositoryInterface, val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavDetailsViewModel::class.java)) {
          FavDetailsViewModel(long,lat,_irepo,context) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}
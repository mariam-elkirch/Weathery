package com.example.weathery.favourite.view

import android.util.Log
import androidx.lifecycle.*
import com.example.weathery.model.Favourite
import com.example.weathery.model.RepositoryInterface


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "AllMoviesFeature"

class FavouriteViewModel (iRepo: RepositoryInterface) : ViewModel() {

    private val _iRepo: RepositoryInterface = iRepo
    private val _favList = MutableLiveData<List<Favourite>>()

    init {
        Log.i(TAG, "instance initializer: Creation of ViewModel")
       // getAllMovies()
    }

    //Expose returned online Data
  //  val onlineMovies: LiveData<List<Favourite>> = _movieList

    fun getAllMovies(){
       /* viewModelScope.launch{
            var favourites = _iRepo.getAllFavourite()
            withContext(Dispatchers.Main){
                Log.i(TAG, "getAllMovies: ${favourites}")
                _favList.postValue()
            }
        }*/
    }
    fun getAllFav(): LiveData<List<Favourite>> {
        Log.i("TAG","view model")
        return _iRepo!!.getAllFavourite()
    }
    //add to fav
    fun insertFav(favourite: Favourite) {
        viewModelScope.launch(Dispatchers.IO){
            Log.i("TAG",favourite.long+"view")
            _iRepo.insertFav(favourite)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ViewModel Cleared")
    }
}
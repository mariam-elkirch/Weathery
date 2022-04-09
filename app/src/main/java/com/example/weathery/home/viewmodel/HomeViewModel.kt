package com.example.weathery.home.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.example.weathery.model.RepositoryInterface
import com.example.weathery.model.Utilitis
import com.example.weathery.model.Weather2
import kotlinx.coroutines.*

private const val TAG = "AllMoviesFeature"

class HomeViewModel (iRepo: RepositoryInterface,context: Context) : ViewModel() {

    private val _iRepo: RepositoryInterface = iRepo
   val mycontext:Context=context
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val errorMessage = MutableLiveData<String>()
    val weatherResponce = MutableLiveData<Weather2>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    init {
        Log.i(TAG, "instance initializer: Creation of ViewModel")
        getWeather()
    }
   // lat:String,long:String,units:String,language:String

    fun getWeather() {


        sharedPreferences = mycontext.getSharedPreferences("Setting",
            Context.MODE_PRIVATE)
        editor =  sharedPreferences.edit()
        val sharedlong = sharedPreferences.getString("longitude","default")
        val sharedlat = sharedPreferences.getString("latitude","default")
        val sharedlanguage= sharedPreferences.getString("language","default")
        val sharedunit= sharedPreferences.getString("units","default")
      //  Log.i("TAG",sharedNameValue+"My Shared Prefrence")
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var response = _iRepo?.getWearherNetwork(sharedlat.toString(),sharedlong.toString(),sharedunit.toString(),sharedlanguage.toString())
            withContext(Dispatchers.Main) {
                if (response?.isSuccessful!!) {
                   weatherResponce.postValue(response?.body())
                    loading.value = false

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun insertWeather(weather2: Weather2){
                viewModelScope.launch(Dispatchers.IO) {
               Log.i("TAG","Vie Model lat insert"+weather2.lat.toString())
                    _iRepo.insertWeather(weather2)
                }
    }
    fun localWeather(): LiveData<List<Weather2>> {
        return _iRepo.getWeather()
    }
    private fun onError(message: String) {
        if(Utilitis.isInternetAvailable(mycontext)){
        errorMessage.value = message
        loading.value = false
    }}

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ViewModel Cleared")
    }
}
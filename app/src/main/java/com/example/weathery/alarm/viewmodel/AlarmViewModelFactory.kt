package com.example.weathery.alarm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathery.model.RepositoryInterface
import java.lang.IllegalArgumentException

class AlarmViewModelFactory(private val _irepo: RepositoryInterface, val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
           AlarmViewModel(_irepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}
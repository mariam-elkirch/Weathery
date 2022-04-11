package com.example.weathery.home.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.work.*
import com.example.weathery.R
import com.example.weathery.alarm.view.AlarmFragment
import com.example.weathery.favourite.view.FavouriteFragment
import com.example.weathery.setting.view.SettingFragment
import com.example.weathery.workmanager.Work

import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //cancel periodic
        sharedPreferences = this.getSharedPreferences("Setting",
            Context.MODE_PRIVATE)
        editor =  sharedPreferences.edit()
       val sharedlanguage= sharedPreferences.getString("language","default")

        WorkManager.getInstance(this).cancelAllWorkByTag("periodic")
        val periodicRequest: PeriodicWorkRequest =
            PeriodicWorkRequest.Builder(Work::class.java, 4, TimeUnit.MINUTES)
                .setInitialDelay(10, TimeUnit.SECONDS) //each 3 min
                .addTag("periodic")
                .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "Mariam",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicRequest
        )
        Log.i("TAG", "In side periodic request setter"+sharedlanguage)

        loadFragment(WeatherFragment())

        bottomNav = findViewById(R.id.bottom_nav) as BottomNavigationView
        sharedPreferences = this.getSharedPreferences("Setting",
            Context.MODE_PRIVATE)
        editor =  sharedPreferences.edit()
        val sharedNameValue = sharedPreferences.getString("language","defaultname")
        Log.i("TAG",sharedNameValue+"My Shared Prefrence")
        if (savedInstanceState == null) {
            val fragment =WeatherFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }
       bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.Weather -> {
                val fragment = WeatherFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.Alarms -> {
                val fragment = AlarmFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.settings -> {
                val fragment = SettingFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.Favourite -> {
                val fragment = FavouriteFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
       true
    }



}

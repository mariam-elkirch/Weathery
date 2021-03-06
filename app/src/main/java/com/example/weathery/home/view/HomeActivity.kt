package com.example.weathery.home.view

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.work.*
import com.example.weathery.R
import com.example.weathery.alarm.view.AlarmFragment
import com.example.weathery.favourite.view.FavouriteFragment
import com.example.weathery.setting.view.SettingFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //cancel periodic
        sharedPreferences = this.getSharedPreferences("Setting",
            Context.MODE_PRIVATE)
        editor =  sharedPreferences.edit()
       val sharedlanguage= sharedPreferences.getString("lang","default")
        val config = this.resources.configuration

        val locale = Locale(sharedlanguage)
        Locale.setDefault(locale)
        config.setLocale(locale)

        this.createConfigurationContext(config)
        this.resources.updateConfiguration(config, this.resources.displayMetrics)

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

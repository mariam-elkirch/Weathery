package com.example.weathery.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.weathery.R
import com.example.weathery.alarm.view.AlarmFragment
import com.example.weathery.favourite.view.FavouriteFragment
import com.example.weathery.map.MapsFragment
import com.example.weathery.setting.view.SettingFragment

import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        loadFragment(WeatherFragment())

        bottomNav = findViewById(R.id.bottom_nav) as BottomNavigationView

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

    /*override fun passDataCom(long: String, lat: String) {
        val bundle = Bundle()
        bundle.putString("long", long)
        bundle.putString("lat", lat)

        val transaction = this.supportFragmentManager.beginTransaction()
        val frag2 =MapsFragment()
        frag2.arguments = bundle

        transaction.replace(R.id.container, frag2)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }*/

}

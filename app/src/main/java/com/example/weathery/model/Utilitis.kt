package com.example.weathery.model

import android.content.Context
import android.location.Address
import android.location.Geocoder

import android.net.ConnectivityManager
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Utilitis {
    companion object{
        fun getAddress(latLng: LatLng,context: Context): String {

            val geocoder = Geocoder(context)
            val addresses: List<Address>?
            val address: Address?
            var addressText = ""

            try {

                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

                if (null != addresses && !addresses.isEmpty()) {
                    address = addresses[0]

                    addressText = address.getAddressLine(0)
                }
            } catch (e: IOException) {
                Log.e("MapsActivity", e.localizedMessage)
            }

            return addressText
        }
     fun convertDayToData(dt: Double?):String{
             val date = Date((dt!! *1000).toLong())
             val format = SimpleDateFormat("EEE",Locale.ENGLISH)
             return format.format(date)
         }
      fun  convertDTtoHour(dt:Int?) :String{
          val unix_seconds: Long = dt!!.toLong()
          //convert seconds to milliseconds
          val date = Date(unix_seconds * 1000L)
          // format of the date
          //val jdf = SimpleDateFormat("EEE yyyy-MM-dd HH:mm")
          val jdf = SimpleDateFormat("HH:mm ")
          jdf.timeZone = TimeZone.getTimeZone("GMT+2")
          val java_date = jdf.format(date).trimIndent()
         return java_date
      }
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }}
}
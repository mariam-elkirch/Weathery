package com.example.weathery.model

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Address
import android.location.Geocoder

import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
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
        fun changeLanguage(lang:String, context: Context){
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val resources: Resources = context.resources
            val config: Configuration = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

        }
        fun getStringAddress(lat:String?,long:String?,context: Context): String {

            val geocoder = Geocoder(context)
            val addresses: List<Address>?
            val address: Address?
            var addressText = ""
            val doublelat: Double = lat!!.toDouble()
            val doublelong: Double = long!!.toDouble()

            try {

                addresses = geocoder.getFromLocation(doublelat, doublelong, 1)

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
    }
        fun convertDateToMillis(dateStr: String?): Long {
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val calendar = Calendar.getInstance()
            try {
                val date = sdf.parse(dateStr)
                calendar.time = date
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return calendar.timeInMillis
        }
         @RequiresApi(Build.VERSION_CODES.O)
        fun convertTimeToLong(time:String):Long{

             //type of med , long time user insert from calender ang compare it with get days
             val localDate = LocalDate.now()

             val timeAndDate="${localDate.dayOfMonth}-"+ localDate.getMonthValue() + "-" + "${localDate.getYear()}" + " " +time
             val timeInMilliseconds: Long
             val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm a")
             var mDate: Date? = null
             try {
                 mDate = sdf.parse(timeAndDate)
             } catch (e: ParseException) {
                 e.printStackTrace()
             }
             Log.i("TAG", "Date m datee: $mDate")
             timeInMilliseconds = mDate!!.time
             return timeInMilliseconds
         }
        fun convertDateAndTimeToFinalTimeInMills(timeAndDate: String?): Long {
            // String timeAndDate = day +"-" + month+"-" + year+" " + hour +":" + minutes ; ;// /-03-2022 12:34";//
            val currentTime = Calendar.getInstance().timeInMillis
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm")
            val timeInMilliseconds: Long
            var mDate: Date? = null
            try {
                mDate = sdf.parse(timeAndDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            Log.i("TAG", "Date m datee: $mDate")
            timeInMilliseconds = mDate!!.time
            println("Date in milli :: $timeInMilliseconds")
            val finalTime = timeInMilliseconds - currentTime
            Log.i("TAG", "final time $finalTime")
            return finalTime
        }

        fun convertDateAndTimeToTimeInMills(timeAndDate: String?): Long {
            // String timeAndDate = day +"-" + month+"-" + year+" " + hour +":" + minutes ; ;// /-03-2022 12:34";//
            val currentTime = Calendar.getInstance().timeInMillis
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm a")
            val timeInMilliseconds: Long
            var mDate: Date? = null
            try {
                mDate = sdf.parse(timeAndDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            Log.i("TAG", "Date m datee: $mDate")
            timeInMilliseconds = mDate!!.time
            return timeInMilliseconds
        }
    }
}
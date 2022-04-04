package com.example.weathery.model

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.NetworkInfo

import android.net.ConnectivityManager
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import java.io.IOException


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
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }}
}
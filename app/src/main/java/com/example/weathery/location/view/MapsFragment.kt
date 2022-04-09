package com.example.weathery.location.view

import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentResultListener
import com.example.weathery.R
import com.example.weathery.favourite.view.FavouriteFragment
import com.example.weathery.model.Utilitis

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException





class MapsFragment : Fragment() , OnMapReadyCallback, GoogleMap.OnMarkerClickListener , View.OnClickListener {
    private lateinit var currentMarker: Marker
    private lateinit var returnLocationToHome: String
    private lateinit var mMap: GoogleMap
    lateinit var txtSearch: TextView
    lateinit var txtLocation: TextView
    lateinit var searchButton: ImageButton
    private lateinit var myLongitude: String
    private lateinit var myLat: String
    private lateinit var type: String
     private lateinit var tv:TextView
     private lateinit var okBtn:ImageButton
    val CUSTOM_PREF_NAME = "Setting"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    companion object{

    }

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myLat = requireArguments().getString("lat")!!
       myLongitude = requireArguments().getString("long")!!

      //  Log.i("TAG",myLat+"mylongfrm mappppppppp")
     /*  arguments?.let {
            myLongitude = it.getString("long")
           myLat = it.getString("lat")
           Log.i("TAG",myLat+"mylongfrm mappppppppp")
        }*/
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v=inflater.inflate(R.layout.fragment_maps, container, false)
        txtSearch=v.findViewById(R.id.EditTextSearch)
        txtLocation=v.findViewById(R.id.textLocation)
         okBtn=v.findViewById(R.id.okButton)
        okBtn.visibility=View.GONE

       sharedPreferences = requireContext().getSharedPreferences(CUSTOM_PREF_NAME,
            Context.MODE_PRIVATE)
      editor =  sharedPreferences.edit()

         parentFragmentManager.setFragmentResultListener("favourite",this, FragmentResultListener {
                 requestKey, result ->type =result.getString("fav","myfav")
             if(type.equals("fav")){
                 okBtn.visibility=View.VISIBLE
                 okBtn.setOnClickListener {
                     val transaction = activity?.supportFragmentManager?.beginTransaction()
                     val args = Bundle()
                     args.putString("area",returnLocationToHome)
                     args.putString("lat",myLat)
                     args.putString("long",myLongitude)
                     parentFragmentManager.setFragmentResult("map",args)
                     transaction?.addToBackStack(null)?.replace(R.id.container, FavouriteFragment())
                     transaction?.commit()
                 }

             }

             Log.i("TAG",type+"favou")})



        parentFragmentManager.setFragmentResultListener("setting",this, FragmentResultListener {
                requestKey, result -> type =result.getString("set","myset")
           myLat=result.getString("lat","myset")
           // tv.setText(type)

            Log.i("TAG",type+"setting"+myLat)})
        parentFragmentManager.setFragmentResultListener("home",this, FragmentResultListener {
                requestKey, result -> type =result.getString("home","myset")

            Log.i("TAG",type+"setting")})
       // myLongitude = arguments?.getString("long")
      //  Log.i("TAG",myLongitude+"mylong")
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
      //  mapFragment=
       searchButton=v.findViewById(R.id.searchButton)
       searchButton.setOnClickListener(this)


        return v
    }





    override fun onMarkerClick(p0: Marker): Boolean {
        return p0.isInfoWindowShown
    }
    fun searchLocation(view: View) {
       currentMarker.remove()
        val geocoder = Geocoder(context)
        val addresses: List<Address>?
        val address: Address?
        val location: String
        location = txtSearch.text.toString().trim()
        if (location == null || location == "") {
            Toast.makeText(context, "provide location", Toast.LENGTH_SHORT).show()
        } else {
            val geoCoder = Geocoder(context)
            try {
                addresses = geoCoder.getFromLocationName(location, 1)
                Log.d("Adddd", "nnnnn" + addresses)
                if (null != addresses && !addresses.isEmpty()) {
                    address = addresses[0]
                    Log.d("Ad2", "" + address.getAddressLine(0))
                    val latLng = LatLng(address.latitude, address.longitude)
                    Log.d("Ad3", "" + latLng)
                    placeMarkerOnMap(latLng)
                    markerDrag()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }
    private fun markerDrag() {
        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {}
            override fun onMarkerDragEnd(marker: Marker) {
                Log.d("====", "latitude : " + marker.position.latitude)

                val newlatLng = LatLng(marker.position.latitude, marker.position.longitude)
                placeMarkerOnMap(newlatLng)
            }

            override fun onMarkerDrag(marker: Marker) {}
        })
    }


    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        val titleStr = context?.let { Utilitis.getAddress(location, context = it) }
        markerOptions.title(titleStr)
        markerOptions.draggable(true)
        Log.d("MActivity", " " + titleStr + " ")
       txtLocation.setText(titleStr)
        currentMarker = mMap.addMarker(markerOptions)!!
        returnLocationToHome = titleStr!!
        myLat= location.latitude.toString()
        myLongitude=location.longitude.toString()
        editor.putString("latitude",myLat)
        editor.putString("longitude",myLongitude)
        editor.apply()
        editor.commit()
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
        Log.i("TAG", " " + returnLocationToHome + " ")

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.setOnMarkerClickListener(this)
        val alex = LatLng(31.2121083, 29.9299856)
        placeMarkerOnMap(alex)


        onLongClick()

        markerDrag()
    }
   fun onLongClick(){
       //currentMarker.remove()
       mMap.setOnMapLongClickListener {
               latlng ->
           mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng))
           val getcoordinates = LatLng(latlng.latitude, latlng.longitude) //catch coordinates from
           currentMarker.remove()
           placeMarkerOnMap(getcoordinates)
           markerDrag()
       }
   }
    override fun onClick(v: View?) {
        val sharedNameValue = sharedPreferences.getString("language","defaultname")
        Log.i("TAG",sharedNameValue+"My Shared Prefrence")
        when (v?.id) {
            R.id.searchButton -> {
               searchLocation(v)
            }
    }
}}
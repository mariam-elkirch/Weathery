package com.example.weathery.setting.view

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.weathery.R
import com.example.weathery.location.viewmodel.LocationViewModel
import com.example.weathery.location.view.MapsFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    val CUSTOM_PREF_NAME = "Setting"

    private val LOCATION_PERMISSION_REQUEST_CODE: Int=200
    lateinit var locationViewModel: LocationViewModel
   lateinit var radio_group: RadioGroup
    lateinit var radio_group_temp: RadioGroup
    lateinit var radio_group_lang: RadioGroup
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor:SharedPreferences.Editor

   val mylong = MutableLiveData<String>()

    var mylat= MutableLiveData<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_setting, container, false)
       // private lateinit var communicator: Communicator
       // comm = requireActivity() as Communicator
       sharedPreferences = requireContext().getSharedPreferences(CUSTOM_PREF_NAME,
            Context.MODE_PRIVATE)
        editor =  sharedPreferences.edit()

        radio_group=view.findViewById(R.id.radio_group)
        radio_group_temp=view.findViewById(R.id.radio_group_temp)
        radio_group_lang=view.findViewById(R.id.radio_group_lang)
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton =view.findViewById(checkedId)
                if(radio.text.equals("GPS"))
                    prepRequestLocationUpdates()
                if(radio.text.equals("MAP")){
                    prepRequestLocationUpdates()
                    goToMapFragment()
                }

                   /* Toast.makeText(activity," On checked change :"+
                            " ${radio.text}",
                        Toast.LENGTH_SHORT).show()*/
            })
        radio_group_temp.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton =view.findViewById(checkedId)
                if(radio.text.equals("Temperature in Ferinheit and wind speed in miles/hour")){
                    editor.putString("units","imperical")
                    editor.apply()
                    editor.commit()
                }

                if(radio.text.equals("Temperature in celesius and wind speed in meters/sec")){
                    editor.putString("units","metric")
                    editor.apply()
                    editor.commit()
                }
                if(radio.text.equals("Temperature in Kelvin and Wind speed in meter/sec")){
                    editor.putString("units","standard")
                    editor.apply()
                    editor.commit()
                }
            })
        radio_group_lang.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton =view.findViewById(checkedId)
                if(radio.text.equals("english")){
                    editor.putString("language","en")
                    editor.apply()
                    editor.commit()
                }

                if(radio.text.equals("arabic")){
                    editor.putString("language","ar")
                    editor.apply()
                    editor.commit()
                }

            })

        val sharedNameValue = sharedPreferences.getString("language","defaultname")
        Log.i("TAG",sharedNameValue+"My Shared Prefrence")
        return view
    }
    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
    private fun requestLocationUpdates() {
       locationViewModel=ViewModelProvider(this).get(LocationViewModel::class.java)
        locationViewModel.getLocationLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
             it.latitude
            it.longitude
            mylat.value=it.latitude
            mylong.value=it.longitude
            editor.putString("latitude",it.latitude)
            editor.putString("longitude",it.longitude)
            editor.apply()
            editor.commit()
           // comm.passDataCom( it.longitude,  it.latitude)
            Log.i("TAG",it.latitude+"  "+it.longitude)
              })

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==  PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates()
                } else {
                    Toast.makeText(context, "Unable to update location without permission", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
    fun goToMapFragment(){

        val args = Bundle()
        args.putString("long",mylong.value)
        args.putString("lat",mylat.value)
        args.putString("set","set")
        val transaction = activity?.supportFragmentManager?.beginTransaction()

        parentFragmentManager.setFragmentResult("setting",args)
        transaction?.addToBackStack(null)?.add(R.id.container, MapsFragment())
        transaction?.commit()
    }


}
package com.example.weathery.home.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathery.R
import com.example.weathery.db.ConcreteLocalSource
import com.example.weathery.favourite.view.FavouriteViewModel
import com.example.weathery.favourite.view.FavouriteViewModelFactory
import com.example.weathery.favouritedetails.view.DailyAdapter
import com.example.weathery.favouritedetails.view.HourlyAdapter
import com.example.weathery.home.viewmodel.HomeViewModel
import com.example.weathery.home.viewmodel.HomeViewModelFactory
import com.example.weathery.location.view.MapsFragment
import com.example.weathery.model.Repository
import com.example.weathery.model.Utilitis
import com.example.weathery.network.Client
import com.example.weathery.setting.view.SettingFragment
import com.google.android.gms.maps.model.LatLng

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var favDeyailsAdapter: DailyAdapter
    lateinit var temperature: TextView
    lateinit var ivCurrentImg: ImageView
    lateinit var locality: TextView
    lateinit var todaydate: TextView
    lateinit var recyclerViewHour: RecyclerView
    lateinit var favDeyailsAdapterHour: HourlyAdapter
    lateinit var chooseBtm:Button

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private var param1: String? = null
    private var param2: String? = null
    var contextt: Context? = null
    lateinit var viewModel: HomeViewModel
    lateinit var allweatherfactory: HomeViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view=inflater.inflate(R.layout.fragment_weather, container, false)
        contextt=container?.context
        locality=view.findViewById(R.id.localityGeo)
        todaydate=view.findViewById(R.id.today_date)
        ivCurrentImg=view.findViewById(R.id.ivcurrentIcon)
        temperature=view.findViewById(R.id.temp_current)
        chooseBtm=view.findViewById(R.id.choose_btn)
        chooseBtm.visibility=View.GONE
       var cv=   view.findViewById<CardView>(R.id.cvcurrentItem)
        recyclerView=view.findViewById(R.id.recycler_view_daily)
        favDeyailsAdapter = context?.let { DailyAdapter( it) }!!

        recyclerViewHour=view.findViewById(R.id.recycler_view_hourly)
        favDeyailsAdapterHour=context?.let { HourlyAdapter( it) }!!

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter =  favDeyailsAdapter

        val horizonalLayoutManager = LinearLayoutManager(context)
        horizonalLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewHour.layoutManager =  horizonalLayoutManager
        recyclerViewHour.adapter =  favDeyailsAdapterHour

        sharedPreferences = requireContext().getSharedPreferences("Setting",
            Context.MODE_PRIVATE)
        editor =  sharedPreferences.edit()
        val sharedlong = sharedPreferences.getString("longitude","default")
        val sharedlat = sharedPreferences.getString("latitude","default")
        val sharedlanguage= sharedPreferences.getString("language","default")
        val sharedunit= sharedPreferences.getString("units","default")
        if(sharedlat.equals("default")&&sharedlong.equals("default")&&
            sharedlanguage.equals("default")&&sharedunit.equals("default")){
            temperature.visibility=View.GONE
            locality.visibility=View.GONE
            cv.visibility=View.GONE
            chooseBtm.visibility=View.VISIBLE
            chooseBtm.setOnClickListener{
                goToSettingFragment()
            }
        }

        allweatherfactory= HomeViewModelFactory(
            Repository.getInstance(
                Client.getInstance(),
                ConcreteLocalSource(contextt!!),
                contextt!!
            ), contextt!!
        )
        viewModel= ViewModelProvider(this,allweatherfactory).get(HomeViewModel::class.java)
        if(Utilitis.isInternetAvailable(requireContext())){
        viewModel.weatherResponce.observe(requireActivity(), {
            Log.i("TAG",it.toString())

            viewModel.insertWeather(it)


           // Log.i("TAG","Locallllllll" +viewModel.localWeather().)
        })}
         viewModel.localWeather().observe(requireActivity()) { mywheather ->
             Log.i("TAG", "Observationnnnnnnnnnnnnnnnnnnnnnnnnnnnn: ${mywheather}")
            // Log.i("TAG","Hi from fav details"+mywheather.)
             if(mywheather.size!=0){
               var local=Utilitis.getStringAddress(sharedlat,sharedlong,requireContext())
             temperature.setText(mywheather.get(0).current?.temp.toString())
             locality.setText(local)
             var dt=Utilitis.convertDTtoHour(mywheather.get(0).current?.dt)
             var url = "https://openweathermap.org/img/wn/${mywheather.get(0).current?.weather?.get(0)?.icon}@2x.png"
             todaydate.setText(dt)
             context?.let {
                 Glide.with(it).load(url)
                     .placeholder(R.drawable.ic_launcher_background)
                     .error(R.drawable.ic_launcher_foreground)
                     .into(ivCurrentImg)
             }

             mywheather.get(0).hourly?.let { it1 -> favDeyailsAdapterHour.setHourlyList(it1) }

             mywheather.get(0).daily?.let { it1 -> favDeyailsAdapter.setDailyList(it1) }
             favDeyailsAdapter.notifyDataSetChanged()

         }
         }
        viewModel.errorMessage.observe(requireActivity(), {
            Toast.makeText(contextt, it, Toast.LENGTH_SHORT).show()
        })

        /*viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })*/

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun goToSettingFragment(){

        val args = Bundle()

        args.putString("home","home")
        val transaction = activity?.supportFragmentManager?.beginTransaction()

        parentFragmentManager.setFragmentResult("home",args)
        transaction?.addToBackStack(null)?.add(R.id.container, SettingFragment())
        transaction?.commit()
    }
}
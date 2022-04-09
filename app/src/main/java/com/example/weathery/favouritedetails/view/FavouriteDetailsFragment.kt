package com.example.weathery.favouritedetails.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathery.R
import com.example.weathery.db.ConcreteLocalSource
import com.example.weathery.favouritedetails.viewmodel.FavDetailsViewModel
import com.example.weathery.favouritedetails.viewmodel.FavDetailsViewModelFactory
import com.example.weathery.model.Repository
import com.example.weathery.model.Utilitis
import com.example.weathery.network.Client

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteDetailsFragment : Fragment() {
    var contextt: Context? = null
    lateinit var recyclerView: RecyclerView
    lateinit var favDeyailsAdapter: DailyAdapter
   lateinit var temperature:TextView
   lateinit var ivCurrentImg:ImageView
    lateinit var locality:TextView
    lateinit var todaydate:TextView
    lateinit var recyclerViewHour: RecyclerView
    lateinit var favDeyailsAdapterHour: HourlyAdapter

    lateinit var viewModel: FavDetailsViewModel
    lateinit var favdetailsfactory: FavDetailsViewModelFactory
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var longitudeFavourite: String
    private lateinit var latitudeFavourite: String
    private lateinit var locationFavourite: String
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
        var view=inflater.inflate(R.layout.fragment_favourite_details, container, false)
        locality=view.findViewById(R.id.localityGeo)
        todaydate=view.findViewById(R.id.today_date)
        ivCurrentImg=view.findViewById(R.id.ivcurrentIcon)
        temperature=view.findViewById(R.id.temp_current)

        parentFragmentManager.setFragmentResultListener("itemFav",this, FragmentResultListener {
                requestKey, result -> longitudeFavourite =result.getString("favlong","myset")
            latitudeFavourite=result.getString("favlat","myset")
            locationFavourite=result.getString("favloc","fav")
            // tv.setText(type)
            contextt=container?.context
           favdetailsfactory= FavDetailsViewModelFactory(longitudeFavourite,latitudeFavourite,
                Repository.getInstance(
                    Client.getInstance(),
                    ConcreteLocalSource(contextt!!),
                    contextt!!
                ), contextt!!
            )
            viewModel= ViewModelProvider(this,favdetailsfactory).get(FavDetailsViewModel::class.java)
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
            viewModel.weatherResponce.observe(requireActivity(), {
                Log.i("TAG","Hi from fav details"+it.daily)
                temperature.setText(it.current?.temp.toString())
               locality.setText(locationFavourite)
                var dt=Utilitis.convertDTtoHour(it.current?.dt)
                var url = "https://openweathermap.org/img/wn/${it.current?.weather?.get(0)?.icon}@2x.png"
                todaydate.setText(dt)
                context?.let {
                    Glide.with(it).load(url)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(ivCurrentImg)
                }

                favDeyailsAdapterHour.setHourlyList(it.hourly)

                favDeyailsAdapter.setDailyList(it.daily)
               favDeyailsAdapter.notifyDataSetChanged()
            })

            viewModel.errorMessage.observe(requireActivity(), {
                Toast.makeText(contextt, it, Toast.LENGTH_SHORT).show()
            })

            Log.i("TAG",latitudeFavourite+"Favourite Details"+longitudeFavourite)})
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavouriteDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouriteDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.example.weathery.alarm.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.weathery.R
import com.example.weathery.alarm.viewmodel.AlarmViewModel
import com.example.weathery.alarm.viewmodel.AlarmViewModelFactory
import com.example.weathery.db.ConcreteLocalSource
import com.example.weathery.favourite.view.FavouriteFragment
import com.example.weathery.favourite.view.FavouriteViewModel
import com.example.weathery.favourite.view.FavouriteViewModelFactory
import com.example.weathery.location.view.MapsFragment
import com.example.weathery.model.Alarm
import com.example.weathery.model.Repository
import com.example.weathery.network.Client
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlarmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlarmFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var contextt: Context? = null
    lateinit var viewModel: AlarmViewModel
    lateinit var allalarmfactory: AlarmViewModelFactory
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_alarm, container, false)
        contextt=container?.context
       allalarmfactory= AlarmViewModelFactory(
            Repository.getInstance(
                Client.getInstance(),
                ConcreteLocalSource(contextt!!), contextt!!

            ))
        viewModel= ViewModelProvider(this,allalarmfactory).get(AlarmViewModel::class.java)
        val fabb: FloatingActionButton = view.findViewById(R.id.fab_alarm)


        fabb.setOnClickListener {
            goToSetAlarmFragment()

        }
        parentFragmentManager.setFragmentResultListener("done",this, FragmentResultListener {
                requestKey, result ->var start =result.getLong("start",1)
            var end=result.getLong("end",1)

            var t=result.getLong("time",1)
              var long=result.getString("long","long")
            sharedPreferences = requireContext().getSharedPreferences("Setting",
                Context.MODE_PRIVATE)
            editor =  sharedPreferences.edit()
            val sharedlat = sharedPreferences.getString("latAlarm","default")
            val sharedLong=sharedPreferences.getString("longAlarm","default")
            Log.i("TAG",sharedlat+"hiii"+sharedLong+"Time SETALARM Time"+t)
            val myalarm=Alarm()
            myalarm.latitude= sharedlat!!
            myalarm.longitude=sharedLong!!
            myalarm.endDate=end
            myalarm.startDate=start
            myalarm.time=t
            viewModel.getAlarms().observe(viewLifecycleOwner){ alarms ->
                if(alarms != null)
                 Log.i("TAM",alarms.get(1).latitude+"inside Alarmssssssss")
                   // favMoviesAdapter.setMovieList(favourites)
               // favMoviesAdapter.notifyDataSetChanged()
            }
           // Log.i("TAG",vi)
            Log.i("TAG","TYPE SETALARM"+start+end)})
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
         * @return A new instance of fragment AlarmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlarmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun goToSetAlarmFragment(){

        val args = Bundle()

        args.putString("alarm","alarm")
        val transaction = activity?.supportFragmentManager?.beginTransaction()

        parentFragmentManager.setFragmentResult("alarm",args)
        transaction?.addToBackStack(null)?.replace(R.id.container, SetAlarmFragment())
        transaction?.commit()
    }
}
package com.example.weathery.alarm.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.weathery.R
import com.example.weathery.alarm.viewmodel.AlarmViewModel
import com.example.weathery.alarm.viewmodel.AlarmViewModelFactory
import com.example.weathery.db.ConcreteLocalSource
import com.example.weathery.favourite.view.FavouriteAdapter
import com.example.weathery.model.Alarm
import com.example.weathery.model.Repository
import com.example.weathery.network.Client
import com.example.weathery.workmanager.OneTimeWorker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import java.util.concurrent.TimeUnit

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
    lateinit var recyclerView: RecyclerView
    lateinit var alarmAdapter: AlarmAdapter
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
        recyclerView=view.findViewById(R.id.recycler_view_alarm)
        alarmAdapter = context?.let { AlarmAdapter(contextt!!) }!!

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = alarmAdapter
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

            var t=result.getString("time","default")
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
                if(alarms != null) {
                    setAlarmsListForManager(alarms)
                    findNextAlarm()
                    alarmAdapter.setAlarmList(alarms)
                    // FindNextAlarm.setContext(contextt!!)
                    // FindNextAlarm.alarmList=alarms
                 //   Log.i("TAM", alarms.get(1).latitude + "inside Alarmssssssss")
                }   // favMoviesAdapter.setMovieList(favourites)
               // favMoviesAdapter.notifyDataSetChanged()
            }
           // Log.i("TAG",vi)
            Log.i("TAG","TYPE SETALARM"+start+end)})
        // Inflate the layout for this fragment
        return view
    }


    fun goToSetAlarmFragment(){

        val args = Bundle()

        args.putString("alarm","alarm")
        val transaction = activity?.supportFragmentManager?.beginTransaction()

        parentFragmentManager.setFragmentResult("alarm",args)
        transaction?.addToBackStack(null)?.replace(R.id.container, SetAlarmFragment())
        transaction?.commit()
    }
    companion object {
       lateinit var alarmsList: List<Alarm>
        fun setAlarmsListForManager(alarmsList: List<Alarm>) {
            this.alarmsList = alarmsList
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        fun findNextAlarm() {
            val currentTime = Calendar.getInstance().timeInMillis
            Log.i("TAG", "current time$currentTime")
            var smallest = currentTime
            var scheduledAlarm: String? = null
            var long:String?=null
            var lat:String?=null
            var timeInMills: Long = 0
         //   Log.i("TAG", "alarm list $alarmsList")
            for (alarm in alarmsList) {
                Log.i("TAG", " ")
                Log.i("TAG", " ")
                timeInMills = alarm.timemills

                Log.i("TAG", "current time$currentTime")
                if (timeInMills - currentTime >= 0 && timeInMills - currentTime < smallest) {
                    smallest = timeInMills - currentTime
                    scheduledAlarm = timeInMills.toString()
                    long=alarm.longitude
                    lat=alarm.latitude
                    Log.i("TAG", "FinfResut If $scheduledAlarm")
                }
            }
            if (scheduledAlarm != null) {
                val currentTime = Calendar.getInstance().timeInMillis
                Log.i("TAG", "In side smallest reminder method")
                val finalTime = timeInMills - currentTime
                val data = Data.Builder()
                    .putString("long",long)
                    .putString("lat",lat)
                    .build()
                val reminderRequest = OneTimeWorkRequest.Builder(OneTimeWorker::class.java)
                    .setInitialDelay(finalTime, TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .addTag("alarms")
                    .build()
                WorkManager.getInstance().enqueue(reminderRequest)
            }

        }
    }
}
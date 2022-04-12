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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.weathery.R
import com.example.weathery.alarm.viewmodel.AlarmViewModel
import com.example.weathery.alarm.viewmodel.AlarmViewModelFactory
import com.example.weathery.db.ConcreteLocalSource
import com.example.weathery.favourite.view.FavouriteFragment
import com.example.weathery.location.view.MapsFragment
import com.example.weathery.model.Alarm
import com.example.weathery.model.Favourite
import com.example.weathery.model.Repository
import com.example.weathery.model.Utilitis
import com.example.weathery.network.Client
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetAlarmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetAlarmFragment : Fragment() {
    val CUSTOM_PREF_NAME = "Setting"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var viewModel: AlarmViewModel
    var contextt: Context? = null
    lateinit var allalarmfactory: AlarmViewModelFactory
    lateinit var startDate: TextView
    lateinit var endDate: TextView
    lateinit var location_tv: TextView
    lateinit var time_tv: TextView
    lateinit var done_btn:Button
    private var param1: String? = null
    private var param2: String? = null
    var flag:Boolean = false
    var user_start:String?=null
    var user_end:String?=null
    private var user_long:String?=null
    private var user_lat:String?=null
    private var user_time:String?=null
    var formate = SimpleDateFormat("dd/M/yyyy", Locale.forLanguageTag("en"))
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
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
        var view=inflater.inflate(R.layout.fragment_set_alarm, container, false)
         startDate=view.findViewById(R.id.tvStartDate)
        endDate=view.findViewById(R.id.tvEndDate)
        location_tv=view.findViewById(R.id.tvLocation)
        time_tv=view.findViewById(R.id.tvTime)
        done_btn=view.findViewById(R.id.doneButton)
      val alarm=Alarm()
        contextt=container?.context
        allalarmfactory= AlarmViewModelFactory(
            Repository.getInstance(
                Client.getInstance(),
                ConcreteLocalSource(contextt!!), contextt!!

            ))
        viewModel= ViewModelProvider(this,allalarmfactory).get(AlarmViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences(CUSTOM_PREF_NAME,
            Context.MODE_PRIVATE)
        editor =  sharedPreferences.edit()

        val now = Calendar.getInstance()
        parentFragmentManager.setFragmentResultListener("alarm",this, FragmentResultListener {
                requestKey, result -> var type =result.getString("alarm","alarm")
            Log.i("TAG",type+"alarm")})
        startDate.setOnClickListener { val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date = formate.format(selectedDate.time)

                alarm.startDate=Utilitis.convertDateToMillis(date)
                user_start=date
                editor.putLong("start_date",alarm.startDate)
                editor.apply()
                editor.commit()
                Toast.makeText(context,""+alarm.startDate, Toast.LENGTH_SHORT).show()
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show() }
        endDate.setOnClickListener {
            val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date = formate.format(selectedDate.time)
                alarm.endDate=Utilitis.convertDateToMillis(date)
                editor.putLong("end_date",alarm.endDate)
                editor.apply()
                editor.commit()
                Toast.makeText(context,"date : " + date, Toast.LENGTH_SHORT).show()
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
        location_tv.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val args = Bundle()
            args.putString("alert","alert")
            parentFragmentManager.setFragmentResult("alert",args)
            transaction?.addToBackStack(null)?.replace(R.id.container, MapsFragment())
            transaction?.commit()
        }
        time_tv.setOnClickListener {
            val timePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)
            Log.i("TAG",timeFormat.format(selectedTime.time))
            alarm.time=timeFormat.format(selectedTime.time)
                editor.putString("time",alarm.time)
                editor.apply()
                editor.commit()
            // btn_show.text = timeFormat.format(selectedTime.time)
        },
            now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timePicker.show() }


        parentFragmentManager.setFragmentResultListener("myalert",this, FragmentResultListener {
                requestKey, result -> var type:String =result.getString("area","myset")
            val long:String=result.getString("long","mylong")
            val lat:String=result.getString("lat","mylong")
            alarm.longitude=long
            alarm.latitude=lat
            editor.putString("longAlarm",long)
            editor.putString("latAlarm",lat)
            editor.apply()
            editor.commit()
            Log.i("TAG",type+"ALERT"+long+" "+lat)
           })

       done_btn.setOnClickListener {

           val transaction = activity?.supportFragmentManager?.beginTransaction()
           val args = Bundle()
           args.putLong("start",alarm.startDate)

           args.putLong("end",alarm.endDate)
           args.putString("time",alarm.time)
           val sharedLong=sharedPreferences.getString("longAlarm","29.924526")
           val sharedLat=sharedPreferences.getString("latAlarm","31.205753")
           val sharedEnd=sharedPreferences.getLong("end_date",1649714400000)
           val sharedStart=sharedPreferences.getLong("start_date",1649973600000)
           val sharedTime=sharedPreferences.getString("time","06:12 PM")
           alarm.endDate=sharedEnd
           alarm.time= sharedTime!!
           alarm.startDate=sharedStart
               alarm.longitude=sharedLong!!
           alarm.latitude= sharedLat!!
           alarm.timemills=Utilitis.convertTimeToLong(alarm.time)
           viewModel.insertAlarm(alarm)
             if(sharedLat.equals("default")||sharedTime.equals("time")||sharedEnd.equals(1)||sharedStart.equals(1)){
                 Toast.makeText(context,"Enter All Fields", Toast.LENGTH_SHORT).show()
             }
           else{
         //  args.putString("long",myLongitude)
           parentFragmentManager.setFragmentResult("done",args)
           transaction?.addToBackStack(null)?.replace(R.id.container, AlarmFragment())
           transaction?.commit()
       }}
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetAlarmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetAlarmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
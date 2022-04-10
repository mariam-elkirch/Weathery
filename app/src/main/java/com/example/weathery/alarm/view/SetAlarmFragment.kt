package com.example.weathery.alarm.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.example.weathery.R
import com.example.weathery.favourite.view.FavouriteFragment
import com.example.weathery.model.Alarm
import com.example.weathery.model.Favourite
import com.example.weathery.model.Utilitis
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
    lateinit var startDate: TextView
    lateinit var endDate: TextView
    lateinit var location_tv: TextView
    lateinit var time_tv: TextView
    lateinit var done_btn:Button
    private var param1: String? = null
    private var param2: String? = null
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
                Toast.makeText(context,"date : " + date, Toast.LENGTH_SHORT).show()
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
        time_tv.setOnClickListener {
            val timePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
            selectedTime.set(Calendar.MINUTE,minute)
            Log.i("TAG",timeFormat.format(selectedTime.time))
            alarm.time=Utilitis.convertTimeToLong(timeFormat.format(selectedTime.time))
            // btn_show.text = timeFormat.format(selectedTime.time)
        },
            now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timePicker.show() }
       done_btn.setOnClickListener {
           val transaction = activity?.supportFragmentManager?.beginTransaction()
           val args = Bundle()
           args.putLong("start",alarm.startDate)
           args.putLong("end",alarm.endDate)
           args.putLong("time",alarm.time)
         //  args.putString("long",myLongitude)
           parentFragmentManager.setFragmentResult("done",args)
           transaction?.addToBackStack(null)?.replace(R.id.container, AlarmFragment())
           transaction?.commit()
       }
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
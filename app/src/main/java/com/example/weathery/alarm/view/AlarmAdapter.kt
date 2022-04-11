package com.example.weathery.alarm.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathery.R
import com.example.weathery.model.Alarm
import com.example.weathery.model.Favourite
import com.example.weathery.model.Utilitis
import java.lang.String

class AlarmAdapter (
var context: Context
) :
RecyclerView.Adapter<ViewHolder>() {
    var alarms = mutableListOf<Alarm>()

    fun setAlarmList(alarm: List<Alarm>) {
        this.alarms = alarm.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.alarm_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarmArray = alarms[position]
        var local=Utilitis.getStringAddress(alarmArray.latitude,alarmArray.longitude,context)
         holder.localTv.setText(local)
       holder.txtTime.setText(alarmArray.time)

    }
    override fun getItemCount(): Int {
        return alarms.size
    }
}
class ViewHolder(var row: View) : RecyclerView.ViewHolder(row) {

    var txtTime: TextView
   var localTv:TextView
    init {
        txtTime = row.findViewById(R.id.texttime)
       localTv=row.findViewById(R.id.textLocationalarm)
    }
}

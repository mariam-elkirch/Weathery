package com.example.weathery.favouritedetails.view

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathery.R
import com.example.weathery.model.DailyItem
import com.example.weathery.model.HourlyItem
import com.example.weathery.model.Utilitis
import com.example.weathery.model.WeatherItem

class HourlyAdapter  (
    var context: Context
) :
    RecyclerView.Adapter<HourViewHolder>() {
    var hourly = mutableListOf<HourlyItem>()
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    fun setHourlyList(hourly: List<HourlyItem>) {


        this.hourly = hourly.toMutableList()


        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HourViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.hourly_item, parent, false)
        return HourViewHolder(view)
    }
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        val hourlyArray = hourly[position]
        // var d=dailyArray.temp.
        holder.min.setText(hourlyArray.temp.toString())
        var dt=Utilitis.convertDTtoHour(hourlyArray.dt)
    holder.hour.setText(dt)
        var url = "https://openweathermap.org/img/wn/${hourlyArray.weather?.get(0)?.icon}@2x.png"
        // holder.cvFavItem.setOnClickListener { onMovieClickListener?.onClick(favouriteArray) }
        context?.let {
            Glide.with(it).load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.hourlyImg)
        }
        sharedPreferences = context.getSharedPreferences("Setting",
            Context.MODE_PRIVATE)
        editor =  sharedPreferences.edit()
        val sharedunit= sharedPreferences.getString("units","default")
        if(sharedunit.equals("imperical"))
            holder.unit.setText("F")
        if(sharedunit.equals("metric"))
            holder.unit.setText("C")
        if(sharedunit.equals("standard"))
            holder.unit.setText("K")
    }
    override fun getItemCount(): Int {
        return hourly.size
    }
}

class HourViewHolder(var row: View) : RecyclerView.ViewHolder(row) {

    var hour: TextView
    var hourlyImg: ImageView
    var min: TextView
    var unit: TextView
    init {
        hour = row.findViewById(R.id.hour)
        hourlyImg = row.findViewById(R.id.ivhourIcon)
        min=row.findViewById(R.id.minh)
        unit=row.findViewById(R.id.unith)
        //favBtn = row.findViewById(R.id.fav_btn)
    }

}

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
import com.example.weathery.model.Utilitis
import com.example.weathery.model.WeatherItem

class DailyAdapter (
var context: Context
) :
RecyclerView.Adapter<ViewHolder>() {
    var daily = mutableListOf<DailyItem>()
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    fun setDailyList(daily: List<DailyItem>) {


            this.daily = daily.toMutableList()


        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.daily_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dailyArray = daily[position]
      // var d=dailyArray.temp.
        var weather: List<WeatherItem?>? = null
        weather=dailyArray.weather
        val myDay=Utilitis.convertDayToData(dailyArray.temp?.day)
        holder.day.setText(myDay)
        holder.describtion.setText(weather?.get(0)?.description)
        holder.min.setText(dailyArray.temp?.min.toString()+"/")
        holder.max.setText(dailyArray.temp?.max.toString())
        var url = "https://openweathermap.org/img/wn/${dailyArray.weather?.get(0)?.icon}@2x.png"
       // holder.cvFavItem.setOnClickListener { onMovieClickListener?.onClick(favouriteArray) }
        context?.let {
            Glide.with(it).load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.dailyImg)
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
        return daily.size
    }
}
class ViewHolder(var row: View) : RecyclerView.ViewHolder(row) {

    var day: TextView
    var describtion: TextView
    var dailyImg: ImageView
    var min:TextView
    var max:TextView
    var unit:TextView
    init {
        day = row.findViewById(R.id.day)
        describtion = row.findViewById(R.id.desc)
        dailyImg = row.findViewById(R.id.ivdailyIcon)
        min=row.findViewById(R.id.min)
        max=row.findViewById(R.id.max)
        unit=row.findViewById(R.id.unit)
        //favBtn = row.findViewById(R.id.fav_btn)
    }
}

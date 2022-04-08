package com.example.weathery.favouritedetails.view

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
import com.example.weathery.model.Favourite
import java.lang.String

class FavouriteAdapter (
var context: Context
) :
RecyclerView.Adapter<ViewHolder>() {
    var favourites = mutableListOf<Favourite>()

    fun setMovieList(movies: List<Favourite>) {
        this.favourites = movies.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.favourite_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favouriteArray = favourites[position]

        holder.txtTitle.setText(favouriteArray.location)
       // holder.cvFavItem.setOnClickListener { onMovieClickListener?.onClick(favouriteArray) }

    }
    override fun getItemCount(): Int {
        return favourites.size
    }
}
class ViewHolder(var row: View) : RecyclerView.ViewHolder(row) {

    var txtTitle: TextView
    var cvFavItem:CardView

    init {
        txtTitle = row.findViewById(R.id.textLocation)
        cvFavItem=row.findViewById(R.id.cvFavouriteItem)

    }
}

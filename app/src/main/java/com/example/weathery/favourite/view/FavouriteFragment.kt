package com.example.weathery.favourite.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import androidx.test.core.app.ApplicationProvider
import com.example.weathery.R
import com.example.weathery.db.ConcreteLocalSource
import com.example.weathery.favouritedetails.view.FavouriteDetailsFragment
import com.example.weathery.location.view.MapsFragment
import com.example.weathery.model.Favourite
import com.example.weathery.model.Repository
import com.example.weathery.network.ApiManager
import com.example.weathery.network.Client


import com.google.android.material.floatingactionbutton.FloatingActionButton
//import androidx.test.core.app.ApplicationProvider.getApplicationContext







// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteFragment : Fragment() ,OnFavClickListener{
   // var appContext: Context = ApplicationProvider
   var contextt: Context? = null
    lateinit var recyclerView: RecyclerView
    lateinit var favMoviesAdapter: FavouriteAdapter

    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewModel: FavouriteViewModel
    lateinit var allfavouritefactory:FavouriteViewModelFactory
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
        var view=inflater.inflate(R.layout.fragment_favourite, container, false)
        contextt=container?.context
        recyclerView=view.findViewById(R.id.recycler_view)
        favMoviesAdapter = context?.let { FavouriteAdapter(this, it) }!!

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = favMoviesAdapter

        // Inflate the layout for this fragment
        allfavouritefactory= FavouriteViewModelFactory(
            Repository.getInstance(
                Client.getInstance(),
                ConcreteLocalSource(contextt!!),
                contextt!!
            ))
        viewModel= ViewModelProvider(this,allfavouritefactory).get(FavouriteViewModel::class.java)
        val fabb: FloatingActionButton = view.findViewById(R.id.fab)
        fabb.setOnClickListener {

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val args = Bundle()
            args.putString("fav","fav")
            parentFragmentManager.setFragmentResult("favourite",args)
            transaction?.addToBackStack(null)?.add(R.id.container, MapsFragment())
            transaction?.commit()
        }
        parentFragmentManager.setFragmentResultListener("map",this, FragmentResultListener {
                requestKey, result -> var type:String =result.getString("area","myset")
                    val long:String=result.getString("long","mylong")
            val lat:String=result.getString("lat","mylong")
            val fav=Favourite(type,long,lat,"")
            viewModel.insertFav(fav)
            Log.i("TAG",type+"setting"+long+" "+lat)})
        viewModel.getAllFav().observe(viewLifecycleOwner){ favourites ->
            if(favourites != null)
               // Log.i("TAG",favourites.get(0).location+"inside favourite")
                favMoviesAdapter.setMovieList(favourites)
            favMoviesAdapter.notifyDataSetChanged()
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
         * @return A new instance of fragment FavouriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(favourite: Favourite) {
       Log.i("TAG","Favourite Click")
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        val args = Bundle()
        args.putString("favlong",favourite.long)
        args.putString("favlat",favourite.lat)
        parentFragmentManager.setFragmentResult("itemFav",args)
        transaction?.addToBackStack(null)?.replace(R.id.container, FavouriteDetailsFragment())
        transaction?.commit()
    }
}
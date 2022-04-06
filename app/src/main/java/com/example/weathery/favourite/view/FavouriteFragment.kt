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
//import androidx.test.core.app.ApplicationProvider
import com.example.weathery.R
import com.example.weathery.db.ConcreteLocalSource
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
class FavouriteFragment : Fragment() {
   // var appContext: Context = ApplicationProvider
   var contextt: Context? = null

    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewModel: FavouriteViewModel
    lateinit var allmoviesfactory:FavouriteViewModelFactory
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
        // Inflate the layout for this fragment
        allmoviesfactory= FavouriteViewModelFactory(
            Repository.getInstance(
                Client.getInstance(),
                ConcreteLocalSource(contextt!!),
                contextt!!
            ))
        viewModel= ViewModelProvider(this,allmoviesfactory).get(FavouriteViewModel::class.java)
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
                    var long:String=result.getString("long","mylong")
            var lat:String=result.getString("lat","mylong")
            var fav=Favourite(type,lat,long)
            viewModel.insertFav(fav)
            Log.i("TAG",type+"setting"+long+" "+lat)})
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
}
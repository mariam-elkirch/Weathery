package com.example.weathery.favouritedetails.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.weathery.R
import com.example.weathery.db.ConcreteLocalSource
import com.example.weathery.favouritedetails.viewmodel.FavDetailsViewModel
import com.example.weathery.favouritedetails.viewmodel.FavDetailsViewModelFactory
import com.example.weathery.home.viewmodel.HomeViewModel
import com.example.weathery.home.viewmodel.HomeViewModelFactory
import com.example.weathery.model.Repository
import com.example.weathery.network.Client

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteDetailsFragment : Fragment() {
    var contextt: Context? = null
    lateinit var viewModel: FavDetailsViewModel
    lateinit var favdetailsfactory: FavDetailsViewModelFactory
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var longitudeFavourite: String
    private lateinit var latitudeFavourite: String
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
        var view=inflater.inflate(R.layout.fragment_favourite_details, container, false)
        parentFragmentManager.setFragmentResultListener("itemFav",this, FragmentResultListener {
                requestKey, result -> longitudeFavourite =result.getString("favlong","myset")
            latitudeFavourite=result.getString("favlat","myset")
            // tv.setText(type)
            contextt=container?.context
           favdetailsfactory= FavDetailsViewModelFactory(
                Repository.getInstance(
                    Client.getInstance(),
                    ConcreteLocalSource(contextt!!),
                    contextt!!
                ), contextt!!
            )
            viewModel= ViewModelProvider(this,favdetailsfactory).get(FavDetailsViewModel::class.java)
            viewModel.weatherResponce.observe(requireActivity(), {
                Log.i("TAG","Hi from fav details"+it.daily)
            })

            viewModel.errorMessage.observe(requireActivity(), {
                Toast.makeText(contextt, it, Toast.LENGTH_SHORT).show()
            })

            Log.i("TAG",latitudeFavourite+"Favourite Details"+longitudeFavourite)})
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
         * @return A new instance of fragment FavouriteDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavouriteDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
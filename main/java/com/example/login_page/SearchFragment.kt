package com.example.login_page

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.login_page.databinding.FragmentSearchBinding
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.gms.common.api.Status

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        val view = binding.root
        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)


        // Fetching API_KEY which we wrapped

        //applicationContext in fragment


        val ai: ApplicationInfo = requireActivity().applicationContext.packageManager
            .getApplicationInfo(requireActivity().applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["keyValue"]
        val apiKey = value.toString()

        // Initializing the Places API
        // with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(requireActivity().applicationContext, apiKey)
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView3.setOnClickListener {
            view.findNavController().navigate(R.id.action_searchFragment_to_parkListFragment)
        }
        binding.imageView9.setOnClickListener {
            view.findNavController().navigate(R.id.action_searchFragment_to_reservationFragment)
        }
        binding.imageView7.setOnClickListener {
            view.findNavController().navigate(R.id.action_searchFragment_to_mapFragment)
        }

        val autocompleteSupportFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?

        // Information that we wish to fetch after typing
        // the location and clicking on one of the options

        autocompleteSupportFragment!!.setPlaceFields(
            listOf(

                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,

                )
        )

        // Display the fetched information after clicking on one of the options
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                // Text view where we will
                // append the information that we fetch

                // Information about the place
                val name = place.name
                val address = place.address
                val latlng = place.latLng
                val latitude = latlng?.latitude
                val longitude = latlng?.longitude
            }

            override fun onError(status: Status) {
                Toast.makeText(requireActivity().applicationContext,status.statusMessage, Toast.LENGTH_SHORT).show()
            }
        })


    }


}
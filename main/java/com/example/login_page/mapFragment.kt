package com.example.login_page

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.login_page.databinding.FragmentAuthBinding
import com.example.login_page.databinding.FragmentMapBinding
import com.example.login_page.viewmodel.MyModel
import com.example.login_page.viewmodel.UserModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class mapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var supportFragmentManager:SupportMapFragment
    lateinit var parksmodel: MyModel
    lateinit var sharedPreferences: SharedPreferences

    val place = Place("sidney", LatLng(-33.852, 151.211))

    // mutable list of places
    val places = mutableListOf<Place>(place)




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater)
        val view = binding.root
        parksmodel = ViewModelProvider(this).get(MyModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        // initialize map fragment
        supportFragmentManager = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val sydney = LatLng(-33.852, 151.211)
//        googleMap.addMarker(
//            MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney")
//        )
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        parksmodel.loadParkings()

        parksmodel.data.observe(requireActivity(), Observer {  data ->
            // async map fragment
            supportFragmentManager.getMapAsync {googleMap ->
                addMarkers(googleMap)
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(36.7346168, 3.1046707)))
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
                googleMap.setOnInfoWindowClickListener { marker ->
                    val id = marker.alpha.toInt()
                    if(marker.title != "You are here") {
                        val bundle = bundleOf("id_parking" to id)
                        parksmodel.park.value = null
                        view.findNavController().navigate(R.id.parkDetailFragment, bundle)
                    }
                }

//                googleMap.setOnMarkerClickListener { marker ->
//                    view.findNavController().navigate(R.id.action_mapFragment_to_parkListFragment)
//                    true
//                }
            }
        })

        val fusedLocationClient= LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY,
            null).addOnSuccessListener { location ->
            if (location != null) { // Récupérer les données de localisation de l’ objet location
                val lat = location.latitude
                val lng = location.longitude
                val latLng = LatLng(lat, lng)

                // add marker to map
                supportFragmentManager.getMapAsync {googleMap ->
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title("You are here")
                    )
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
                }
            }
        }


        // onclick listner  for the button
        binding.imageView3.setOnClickListener {
            view.findNavController().navigate(R.id.action_mapFragment_to_parkListFragment)
        }

        // onclick listner  for the imageview9
        binding.imageView9.setOnClickListener {
            view.findNavController().navigate(R.id.action_mapFragment_to_reservationFragment)
        }

        // onclick listner  for the imageview8 -- search button
        binding.imageView8.setOnClickListener {
            view.findNavController().navigate(R.id.action_mapFragment_to_searchFragment)
        }
    }

    private fun addMarkers(googleMap: GoogleMap) {
        //for each park in parksmodel.data.value add marker
        for (park in parksmodel.data.value!!) {
            var etat = ""
            if(park.etat == 0) {
                etat = "Fermé"
            } else {
                etat = "Ouvert"
            }

            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(park.lat, park.lng))
                    .title(park.nom)
                    .snippet(etat)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .alpha(park.id_parking.toFloat())
            )
        }
    }

}
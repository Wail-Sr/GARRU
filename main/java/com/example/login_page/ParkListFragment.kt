package com.example.parkpage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login_page.adapter.Adapter
import com.example.login_page.MainActivity4
import com.example.login_page.R
import com.example.login_page.databinding.FragmentParkListBinding
import com.example.login_page.login_act
import com.example.login_page.viewmodel.MyModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

class ParkListFragment : Fragment() {

    private lateinit var binding: FragmentParkListBinding
    lateinit var adapter:Adapter

    lateinit var sharedPreferences: SharedPreferences
    var isConnected = false
    lateinit var model: MyModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentParkListBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(MyModel::class.java)
        model.park.value = null
        model.days.value = null
        model.loadParkings()
        val layoutManager = LinearLayoutManager(requireActivity(),  RecyclerView.VERTICAL,false)
        binding.recyclerView.layoutManager = layoutManager
        adapter = Adapter(requireActivity())
        binding.recyclerView.adapter = adapter

        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isConnected = sharedPreferences.getBoolean("CONNECTED", false)

        binding.imageView9.setOnClickListener{
            if(!isConnected){
                val intent = Intent(context, login_act::class.java)
                startActivity(intent)
            }else{
                view.findNavController().navigate(R.id.action_parkListFragment_to_reservationFragment)
            }
        }

        binding.imageView6.setOnClickListener {
            view.findNavController().navigate(R.id.action_parkListFragment_to_mapFragment)
        }

        // onclick listner  for the imageview9 -- search
        binding.imageView8.setOnClickListener {
            view.findNavController().navigate(R.id.action_parkListFragment_to_searchFragment)
        }


        // add Observers
        // loading observer
        model.loading.observe(requireActivity(), Observer {  loading->
            if(loading) {
                binding.progressBar.visibility = View.VISIBLE
            }
            else {
                binding.progressBar.visibility = View.GONE
            }

        })
        // Error message observer
        model.errorMessage.observe(requireActivity(), Observer {  message ->
            //Toast.makeText(requireContext(),"Une erreur s'est produite",Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()

        })


        // List parks observer
        model.data.observe(requireActivity(), Observer {  data ->
            adapter.setParkings(data)
        })


    }
}
package com.example.login_page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login_page.adapter.Adapter
import com.example.login_page.adapter.ReservationAdapter
import com.example.login_page.databinding.FragmentAuthBinding
import com.example.login_page.databinding.FragmentReservationBinding
import com.example.login_page.viewmodel.MyModel
import com.example.login_page.viewmodel.ReservationsModel
import com.example.login_page.viewmodel.UserModel

class ReservationFragment : Fragment() {

    private lateinit var binding: FragmentReservationBinding
    lateinit var reservationmodel: ReservationsModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var reservationAdapter: ReservationAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentReservationBinding.inflate(layoutInflater)
        val view = binding.root
        reservationmodel = ViewModelProvider(this).get(ReservationsModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reservationmodel = ViewModelProvider(requireActivity()).get(ReservationsModel::class.java)

        reservationmodel.loadReservations(sharedPreferences.getInt("USERID", 1))

        val layoutManager = LinearLayoutManager(requireActivity(),  RecyclerView.VERTICAL,false)
        binding.reservations.layoutManager = layoutManager
        reservationAdapter = ReservationAdapter(requireActivity())
        binding.reservations.adapter = reservationAdapter

        binding.imageView3.setOnClickListener{
            view.findNavController().navigate(R.id.action_reservationFragment_to_parkListFragment3)
        }

        // map page
        binding.imageView7.setOnClickListener{
            view.findNavController().navigate(R.id.action_reservationFragment_to_mapFragment)
        }


        // search page
        binding.imageView8.setOnClickListener{
            view.findNavController().navigate(R.id.action_reservationFragment_to_searchFragment)
        }


        binding.button5.setOnClickListener{
            sharedPreferences.edit{
                putBoolean("CONNECTED",false)
            }

            view.findNavController().navigate(R.id.action_reservationFragment_to_parkListFragment)
        }

        reservationmodel.loading.observe(requireActivity(), Observer {  loading->
            if(loading) {
                binding.progress.visibility = View.VISIBLE
            }
            else {
                binding.progress.visibility = View.GONE
            }

        })
        // Error message observer
        reservationmodel.errorMessage.observe(requireActivity(), Observer {  message ->
            //Toast.makeText(requireContext(),"Une erreur s'est produite",Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()

        })


        // List parks observer
        reservationmodel.reservations.observe(requireActivity(), Observer {  data ->
            reservationAdapter.setReservs(data)
        })



    }


    fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}
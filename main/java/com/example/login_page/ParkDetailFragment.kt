package com.example.parkpage

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login_page.adapter.DaysAdapter
import com.example.login_page.R
import com.example.login_page.databinding.FragmentParkDetailBinding
import com.example.login_page.entity.Day
import com.example.login_page.viewmodel.MyModel

class ParkDetailFragment : Fragment() {

    private lateinit var binding: FragmentParkDetailBinding

    lateinit var model: MyModel

    lateinit var distancee: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentParkDetailBinding.inflate(layoutInflater)
        val view = binding.root

        model = ViewModelProvider(requireActivity()).get(MyModel::class.java)

        val id_parking = arguments?.get("id_parking") as Int

        model.loadParkingById(id_parking)
        model.loaddays(id_parking)


        val layoutManager = LinearLayoutManager(requireActivity(),  RecyclerView.HORIZONTAL,false)
        binding.days.layoutManager = layoutManager

        model.park.observe(viewLifecycleOwner, Observer { parking->

        binding.apply {

            Glide.with(requireActivity()).load(parking?.image).into(parkimage)

            if (parking?.etat == 0){
                parketat.text = "Fermé"
                parketat.setBackgroundResource(R.drawable.etattext_background)
            }
            else{
                parketat.text = "Ouvert"
                parketat.setBackgroundResource(R.drawable.etattext_background2)
            }

            parkname.text = parking?.nom
            parkplace.text = parking?.commune
            parkdistance.text = parking?.distance.toString() + " Km"
            parktaux.text = parking?.taux.toString() + " %"
            parkprice.text = parking?.tarif.toString() + " Da/Hr"

        }

        })

        // observer for days
        model.days.observe(viewLifecycleOwner, Observer { days ->
            if(days != null){
                binding.days.adapter = DaysAdapter(requireActivity(), days)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.parkroute.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:"+model.park.value?.lat+"," + model.park.value?.lat + "?q="+model.park.value?.nom+" "+model.park.value?.commune)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

    }
}
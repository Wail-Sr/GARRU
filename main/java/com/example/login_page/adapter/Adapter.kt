package com.example.login_page.adapter

import android.Manifest
import android.R.attr.apiKey
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login_page.R
import com.example.login_page.databinding.ListLayoutBinding
import com.example.login_page.entity.Parking
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.android.SphericalUtil
import com.google.maps.model.DirectionsResult
import com.google.maps.model.Duration
import com.google.maps.model.TravelMode


class Adapter(val context:Context) : RecyclerView.Adapter<Adapter.MyViewHolder> (){

    var data = mutableListOf<Parking>()
    var position:LatLng? = null

    class MyViewHolder(val binding: ListLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(ListLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        bind(holder,data[position])
    }

    private fun bind(holder: MyViewHolder, parking: Parking) {

        var distancee: String

        holder.binding.apply {
            name.text = parking.nom
            commune.text = parking.commune
            if (parking.etat == 0){
                etat.text = "Fermé"
                etat.setTextColor(Color.parseColor("#F14A27"))
            }
            else{
                etat.text = "Ouvert"
                etat.setTextColor(Color.parseColor("#27F18C"))
            }
            taux.text = parking.taux.toString() + " %"

            // Récupération de la position du parking

            //distance.text =  parking.distance.toString()

            getLocation(distance, LatLng(parking.lat, parking.lng))

            temps.text = parking?.temps

            Glide.with(context).load(parking.image).into(image);
        }

        holder.itemView.setOnClickListener{

            val bundle = bundleOf("id_parking" to parking.id_parking)
            it.findNavController().navigate(R.id.parkDetailFragment, bundle)

        }

    }

    fun setParkings(parks: List<Parking>) {
        this.data = parks.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    private fun getLocation(distance: TextView, parkPos:LatLng){

        val fusedLocationClient= LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null).addOnSuccessListener { location ->
            if (location != null) { // Récupérer les données de localisation de l’ objet location
                val lat = location.latitude
                val lng = location.longitude
                var latLng = LatLng(lat, lng)
                distance.text = SphericalUtil.computeDistanceBetween(latLng, parkPos).toString()

            }
        }
    }
}
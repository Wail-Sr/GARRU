package com.example.login_page.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login_page.R
import com.example.login_page.databinding.DayLayoutBinding
import com.example.login_page.databinding.ReservationLayoutBinding
import com.example.login_page.entity.Day
import com.example.login_page.entity.Parking
import com.example.login_page.entity.Reservation

class ReservationAdapter(val context: Context) : RecyclerView.Adapter<ReservationAdapter.MyViewHolder> () {

    var data = mutableListOf<Reservation>()

    class MyViewHolder(val binding: ReservationLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationAdapter.MyViewHolder {
        return ReservationAdapter.MyViewHolder(
            ReservationLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReservationAdapter.MyViewHolder, position: Int) {
        bind(holder,data[position])
    }

    private fun bind(holder: ReservationAdapter.MyViewHolder, reservation: Reservation) {

        var distancee: String

        holder.binding.apply {

            parkname.text = reservation.parkingname
            dayselected.text = reservation.day
            opentime2.text = reservation.open_time
            closetime2.text = reservation.close_time
        }

        holder.itemView.setOnClickListener{

            val bundle = bundleOf("id_reservation" to reservation.id_reservation)
            it.findNavController().navigate(R.id.QRFragment, bundle)

        }

    }

    override fun getItemCount() = data.size

    fun setReservs(reservation: List<Reservation>) {
        this.data = reservation.toMutableList()
        notifyDataSetChanged()
    }

}
package com.example.login_page.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login_page.entity.Day
import com.example.login_page.databinding.DayLayoutBinding

class DaysAdapter(val context:Context, var days:List<Day>) : RecyclerView.Adapter<DaysAdapter.MyViewHolder> () {

    class MyViewHolder(val binding: DayLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(DayLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            dayname.text = days[position].day
            opentime.text = days[position].open_time
            closetime.text = days[position].close_time
        }
    }

    override fun getItemCount() = days.size


}
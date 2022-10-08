package com.example.login_page.localData

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "reservation_table")
data class Reserv(
    @PrimaryKey
    val id_reservation:Int,
    val id_parking:Int,
    val id_user:Int,
    val parkingname:String,
    val day:String,
    val open_time:String,
    val close_time:String,
)

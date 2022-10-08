package com.example.login_page.entity

import java.io.Serializable

data class Reservation(
    val id_reservation:Int,
    val id_parking:Int,
    val id_user:Int,
    val parkingname:String,
    val day:String,
    val open_time:String,
    val close_time:String,

    ) : Serializable
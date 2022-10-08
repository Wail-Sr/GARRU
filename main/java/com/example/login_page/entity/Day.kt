package com.example.login_page.entity

import java.io.Serializable

data class Day(
    val day_id:Int,
    val id_parking:Int,
    val day:String,
    val open_time:String,
    val close_time: String
    ) : Serializable

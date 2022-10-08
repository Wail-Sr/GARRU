package com.example.login_page.entity

import java.io.Serializable

data class Parking(
    val id_parking:Int,
    val nom:String,
    val commune:String,
    val etat:Int,
    val taux:Double,
    var distance:Double,
    val temps:String,
    val tarif:Double,
    val lat:Double,
    val lng:Double,
    val places:Int,
    val image:String,

    ) : Serializable
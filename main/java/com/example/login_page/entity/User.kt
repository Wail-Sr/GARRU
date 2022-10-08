package com.example.login_page.entity

import java.io.Serializable

data class User(
    val user_id:Int,
    val name:String,
    val last_name:String,
    val tel:String,
    val email:String,
    val password: String
) : Serializable


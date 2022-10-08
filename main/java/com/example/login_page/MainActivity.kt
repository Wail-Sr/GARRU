package com.example.login_page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import com.example.login_page.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var isConnected = false



    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isConnected = sharedPreferences.getBoolean("CONNECTED", false)

        if(isConnected){
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
            finish()
        }

        binding.button.setOnClickListener{
            val intent = Intent(this, login_act::class.java)
            startActivity(intent)
            finish()
        }
    }

}
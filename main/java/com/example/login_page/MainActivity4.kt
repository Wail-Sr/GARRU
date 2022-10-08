package com.example.login_page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import com.example.login_page.databinding.ActivityMain4Binding
import com.example.login_page.databinding.ActivityMainBinding

class MainActivity4 : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var isConnected = false

    private lateinit var binding: ActivityMain4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imageView3.setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        binding.button5.setOnClickListener{
            sharedPreferences.edit{
                putBoolean("CONNECTED",false)
            }

            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
            finish()
        }
    }
}
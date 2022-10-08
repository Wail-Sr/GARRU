package com.example.login_page.viewmodel

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.example.login_page.entity.Parking
import androidx.lifecycle.MutableLiveData
import com.example.login_page.entity.Day
import com.example.movieexample.retrofit.ParkEndPoint
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class MyModel: ViewModel() {

    var park = MutableLiveData<Parking>()
    var days = MutableLiveData<List<Day>>()
    var data = MutableLiveData<List<Parking>>()
    //var position = MutableLiveData<LatLng>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable.localizedMessage)
    }

    private fun onError(message: String?) {
        errorMessage.postValue(message!!)
        loading.postValue(false)
    }

    fun loadParkings() {
        if(data.value==null) {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = ParkEndPoint.createInstance().getAllParkings()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        loading.value = false
                        data.postValue(response.body())


                    } else {
                        onError(response.message())
                    }
                }
            }

        }
    }

    fun loadParkingById(id:Int) {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ParkEndPoint.createInstance().getParkingsById(id_parking = id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    park.postValue(response.body())
                } else {
                    onError(response.message())
                }
            }

        }
    }

    fun loaddays(id_parking:Int){
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = ParkEndPoint.createInstance().getDays(id_parking = id_parking)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    days.postValue(response.body())
                } else {
                    onError(response.message())
                }
            }

        }
    }

//    fun getPosition(){
//        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
//            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(Activity())
//            if (ActivityCompat.checkSelfPermission(
//                    Activity(),
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    Activity(),
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // request permission
//                ActivityCompat.requestPermissions(
//                    Activity(),
//                    arrayOf(
//                        android.Manifest.permission.ACCESS_FINE_LOCATION,
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION
//                    ),
//                    1
//                )
//            }
//            withContext(Dispatchers.Main) {
//                fusedLocationClient.getCurrentLocation(
//                    Priority.PRIORITY_HIGH_ACCURACY,
//                    null
//                ).addOnSuccessListener { location ->
//                    if (location != null) { // Récupérer les données de localisation de l’ objet location
//                        val lat = location.latitude
//                        val lng = location.longitude
//                        position.postValue(LatLng(lat, lng))
//
//                        // update each parking distance in data list
//                        for (park in data.value!!) {
//                            park.distance = SphericalUtil.computeDistanceBetween(
//                                position.value,
//                                LatLng(park.lat, park.lng)
//                            )
//                        }
//
//                    }
//                }
//
//            }
//        }
//    }
    
}
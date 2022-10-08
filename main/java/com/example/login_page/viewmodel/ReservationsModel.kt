package com.example.login_page.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login_page.entity.Reservation
import com.example.login_page.retrofit.ReservationEndPoint
import kotlinx.coroutines.*

class ReservationsModel: ViewModel()  {

    var reservations = MutableLiveData<List<Reservation>>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable.localizedMessage)
    }
    private fun onError(message: String?) {
        errorMessage.postValue(message!!)
        loading.postValue(false)
    }

    fun loadReservations(id_user:Int) {
        if(reservations.value==null) {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = ReservationEndPoint.createInstance().getAllReservations(id_user = id_user)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        loading.value = false
                        reservations.postValue(response.body())
                    } else {
                        onError(response.message())
                    }
                }
            }

        }
    }

}
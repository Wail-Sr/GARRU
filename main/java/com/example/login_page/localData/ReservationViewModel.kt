package com.example.login_page.localData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationViewModel(application: Application): AndroidViewModel(application) {

    val reservations = MutableLiveData<List<Reserv>>()
    val repository: ReservationRepository

    init {
        val reservationDao = ReservationDatabase.getDatabase(application).reservationDao()
        repository = ReservationRepository(reservationDao)
    }

    fun getAllReservations(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.allReservations()
            withContext(Dispatchers.Main) {
                reservations.postValue(response.value)
            }
        }
    }

    fun insert(reservation: Reserv){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(reservation)
        }
    }
}
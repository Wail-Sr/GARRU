package com.example.login_page.localData

import androidx.lifecycle.MutableLiveData

class ReservationRepository(private val reservationDao: ReservationDao) {

    suspend fun allReservations(): MutableLiveData<List<Reserv>> = reservationDao.getAllReservations()

    suspend fun insert(reservation: Reserv) {
        reservationDao.addReservation(reservation)
    }

}
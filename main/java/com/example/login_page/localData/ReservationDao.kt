package com.example.login_page.localData

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReservation(reservation: Reserv)

    @Query("SELECT * FROM reservation_table ORDER BY id_reservation ASC")
    suspend fun getAllReservations(): MutableLiveData<List<Reserv>>

}
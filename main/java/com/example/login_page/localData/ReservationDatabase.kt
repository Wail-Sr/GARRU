package com.example.login_page.localData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Reserv::class], version = 1, exportSchema = false)
abstract class ReservationDatabase: RoomDatabase() {

    abstract fun reservationDao(): ReservationDao

    companion object {
        // ReservationDatabase is a singleton
        @Volatile
        private var INSTANCE: ReservationDatabase? = null

        fun getDatabase(context: Context): ReservationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReservationDatabase::class.java,
                    "reservation_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

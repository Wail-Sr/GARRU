package com.example.login_page.retrofit

import com.example.login_page.entity.Parking
import com.example.login_page.entity.Reservation
import com.example.login_page.url
import com.example.movieexample.retrofit.ParkEndPoint
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path



sealed interface ReservationEndPoint{
    @GET("reservations/getall/{id_user}")
    suspend fun getAllReservations(@Path("id_user") id_user: Int): Response<List<Reservation>>

    companion object {
        @Volatile
        var endpoint: ReservationEndPoint? = null
        fun createInstance(): ReservationEndPoint {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(ReservationEndPoint::class.java)
                }
            }
            return endpoint!!

        }

    }
}

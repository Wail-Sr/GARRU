package com.example.movieexample.retrofit

import com.example.login_page.entity.Day
import com.example.login_page.entity.Parking
import com.example.login_page.entity.User
import com.example.login_page.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ParkEndPoint {

    @GET("parkings/getall")
    suspend fun getAllParkings(): Response<List<Parking>>

    @GET("parkings/getalldays/{id_parking}")
    suspend fun getDays(@Path("id_parking") id_parking: Int): Response<List<Day>>

    @GET("parkings/getbyid/{id_parking}")
    suspend fun getParkingsById(@Path("id_parking") id_parking: Int): Response<Parking>

    companion object {
        @Volatile
        var endpoint: ParkEndPoint? = null
        fun createInstance(): ParkEndPoint {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(ParkEndPoint::class.java)
                }
            }
            return endpoint!!

        }

    }

}

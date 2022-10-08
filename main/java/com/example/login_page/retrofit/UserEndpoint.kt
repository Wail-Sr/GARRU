package com.example.login_page.retrofit

import com.example.login_page.entity.Emailandmdp
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

interface UserEndpoint {

    @GET("users/getall")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("users/getById/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

    @GET("users/getByEmail/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<List<User>>

    // get user by email and mdp
    @POST("users/getuser")
    suspend fun getUserByEmailAndMdp(@Body  emailandmdp: Emailandmdp): Response<User>

    @POST("users/addUser")
    suspend fun addUser(@Body user: User): Response<List<User>>

    @GET("users/getbyemailandmdp/{email}/{password}")
    suspend fun getuser(@Path("email") email: String, @Path("password") password: String): Response<User>


    companion object {
        @Volatile
        var endpoint: UserEndpoint? = null
        fun createInstance(): UserEndpoint {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(UserEndpoint::class.java)
                }
            }
            return endpoint!!

        }

    }

}

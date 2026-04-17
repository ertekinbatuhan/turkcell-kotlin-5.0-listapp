package com.example.turkcelllistapp.data.remote

import com.example.turkcelllistapp.data.model.User
import retrofit2.http.GET

/**
 * Retrofit API arayüzü — JSONPlaceholder üzerinden kullanıcı verisi çeker.
 * Endpoint path'leri [Endpoints] objesinden alınır.
 */
interface ApiService {

    @GET(Endpoints.Users.LIST)
    suspend fun getUsers(): List<User>
}

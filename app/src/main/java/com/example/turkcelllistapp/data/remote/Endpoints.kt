package com.example.turkcelllistapp.data.remote

/**
 * API endpoint yapılandırması.
 * Base URL ve tüm endpoint path'leri merkezi olarak burada tanımlanır.
 * Yeni bir endpoint eklendiğinde sadece bu dosya güncellenir.
 */
object Endpoints {

    const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    object Users {
        const val LIST = "users"
    }
}

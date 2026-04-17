package com.example.turkcelllistapp.data.repository

import com.example.turkcelllistapp.data.model.User
import com.example.turkcelllistapp.data.remote.ApiService
import com.example.turkcelllistapp.data.remote.NetworkError
import com.example.turkcelllistapp.data.remote.NetworkResult

/**
 * Veri katmanı ile ViewModel arasındaki köprü.
 * API çağrısını [NetworkResult] ile sararak hata yönetimini merkezileştirir.
 * İleride cache veya local DB eklenirse burada yönetilir.
 */
class UserRepository(private val api: ApiService) {

    suspend fun getUsers(): NetworkResult<List<User>> {
        return try {
            val users = api.getUsers()
            NetworkResult.Success(users)
        } catch (e: Exception) {
            val error = NetworkError.from(e)
            NetworkResult.Failure(error)
        }
    }
}

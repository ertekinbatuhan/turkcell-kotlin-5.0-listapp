package com.example.turkcelllistapp.data.remote

/**
 * API çağrılarının sonucunu saran wrapper.
 * Repository'den ViewModel'e temiz bir şekilde başarı/hata durumu aktarır.
 */
sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Failure(val error: NetworkError) : NetworkResult<Nothing>
}

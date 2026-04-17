package com.example.turkcelllistapp.viewmodel

import com.example.turkcelllistapp.data.model.User

/**
 * Kullanıcı listesi ekranının olası UI durumları.
 * ViewModel'den UI'a tek bir StateFlow üzerinden aktarılır.
 */
sealed interface UserUiState {
    data object Loading : UserUiState
    data class Success(val users: List<User>) : UserUiState
    data class Error(val message: String) : UserUiState
}

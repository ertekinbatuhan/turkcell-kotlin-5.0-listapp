package com.example.turkcelllistapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turkcelllistapp.data.model.User
import com.example.turkcelllistapp.data.remote.NetworkResult
import com.example.turkcelllistapp.data.repository.UserRepository
import com.example.turkcelllistapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Kullanıcı listesi için ViewModel — API çağrısı, arama filtresi ve
 * pull-to-refresh işlemlerini yönetir.
 * Repository'den gelen [NetworkResult] üzerinden hata tipini belirler.
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    /**
     * uiState ve searchQuery'yi birleştirerek filtrelenmiş kullanıcı listesi üretir.
     * WhileSubscribed sayesinde UI arka plana geçince collect durur.
     */
    val filteredUsers: StateFlow<List<User>> = combine(_uiState, _searchQuery) { state, query ->
        when (state) {
            is UserUiState.Success -> {
                val q = query.trim()
                if (q.isEmpty()) state.users
                else state.users.filter { user ->
                    user.name.contains(q, ignoreCase = true) ||
                        user.email.contains(q, ignoreCase = true)
                }
            }
            else -> emptyList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(Constants.FLOW_SUBSCRIBE_TIMEOUT),
        initialValue = emptyList()
    )

    init {
        loadUsers()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    /** İlk yükleme ve "Tekrar Dene" için — Loading state gösterir. */
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            handleResult(repository.getUsers())
        }
    }

    /** Pull-to-refresh — mevcut listeyi gösterirken arka planda yeniler. */
    fun refreshUsers() {
        viewModelScope.launch {
            _isRefreshing.value = true
            handleResult(repository.getUsers())
            _isRefreshing.value = false
        }
    }

    /** NetworkResult'ı UI state'e dönüştürür — tekrar eden try-catch'i ortadan kaldırır. */
    private fun handleResult(result: NetworkResult<List<User>>) {
        _uiState.value = when (result) {
            is NetworkResult.Success -> UserUiState.Success(result.data)
            is NetworkResult.Failure -> UserUiState.Error(result.error.userMessage)
        }
    }
}

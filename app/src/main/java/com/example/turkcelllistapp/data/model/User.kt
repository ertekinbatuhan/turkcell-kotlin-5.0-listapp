package com.example.turkcelllistapp.data.model

/**
 * JSONPlaceholder /users endpoint'inden dönen kullanıcı modeli.
 * Gson tarafından otomatik olarak deserialize edilir.
 */
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
)

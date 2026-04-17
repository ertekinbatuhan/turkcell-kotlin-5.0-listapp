package com.example.turkcelllistapp.data.remote

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Ağ hatalarını kategorize eden enum.
 * Her hata tipinin kullanıcıya gösterilecek mesajı bulunur.
 * Repository katmanında exception'lar bu enum'a dönüştürülür.
 */
enum class NetworkError(val userMessage: String) {
    NO_INTERNET("İnternet bağlantınız yok gibi görünüyor. Wi-Fi veya mobil verinizi kontrol edip tekrar deneyin."),
    TIMEOUT("Bağlantı zaman aşımına uğradı. İnternet bağlantınız yavaş olabilir, lütfen tekrar deneyin."),
    SERVER_ERROR("Şu anda sunucuya ulaşılamıyor. Lütfen birkaç dakika sonra tekrar deneyin."),
    UNKNOWN("Bir şeyler ters gitti. Lütfen uygulamayı kapatıp tekrar açın veya daha sonra deneyin.");

    companion object {
        /** Exception tipine göre uygun NetworkError enum değerini döndürür. */
        fun from(exception: Throwable): NetworkError = when (exception) {
            is UnknownHostException -> NO_INTERNET
            is SocketTimeoutException -> TIMEOUT
            is IOException -> NO_INTERNET
            else -> UNKNOWN
        }
    }
}

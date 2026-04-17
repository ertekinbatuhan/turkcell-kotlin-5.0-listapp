package com.example.turkcelllistapp.utils

import androidx.compose.ui.unit.dp

/**
 * Uygulama genelinde kullanılan sabit değerler.
 * Tüm hardcoded string, boyut ve API bilgileri buradan yönetilir.
 */
object Constants {

    // Navigation route'ları
    const val NAV_USER_FLOW = "user_flow"
    const val NAV_USER_LIST = "user_list"
    const val NAV_USER_DETAIL = "user_detail/{userId}"
    const val NAV_ARG_USER_ID = "userId"
    fun userDetailRoute(userId: Int) = "user_detail/$userId"

    // StateFlow subscribe timeout
    const val FLOW_SUBSCRIBE_TIMEOUT = 5_000L

    // Ekran başlıkları
    const val TITLE_USER_LIST = "Kullanıcılar"
    const val TITLE_USER_DETAIL = "Kullanıcı Detayı"
    const val TITLE_CONTACT_INFO = "İletişim Bilgileri"

    // Arama çubuğu
    const val SEARCH_PLACEHOLDER = "İsim veya e-posta ile ara…"

    // Tema açıklamaları
    const val THEME_SYSTEM_DESC = "Sistem teması"
    const val THEME_LIGHT_DESC = "Açık tema"
    const val THEME_DARK_DESC = "Koyu tema"

    // Buton & aksiyon metinleri
    const val ACTION_RETRY = "Tekrar Dene"
    const val ACTION_CLEAR = "Temizle"
    const val ACTION_BACK = "Geri"

    // Hata ekranı başlığı (detaylı mesaj NetworkError enum'undan gelir)
    const val ERROR_CONNECTION = "Bağlantı hatası"

    // Yükleniyor / bulunamadı mesajları
    const val MSG_LOADING = "Yükleniyor…"
    const val MSG_USER_NOT_FOUND = "Kullanıcı bulunamadı."
    const val MSG_USER_LOAD_FAILED = "Kullanıcı yüklenemedi."
    const val MSG_SEARCH_EMPTY_TITLE = "Sonuç bulunamadı"
    const val MSG_SEARCH_EMPTY_SUBTITLE = "Aradığınız kriterlere uygun kullanıcı yok. Farklı bir isim veya e-posta deneyin."

    // Detay ekranı label'ları
    const val LABEL_EMAIL = "E-posta"
    const val LABEL_PHONE = "Telefon"
    const val LABEL_WEBSITE = "Web Sitesi"
    const val LABEL_USERNAME = "Kullanıcı Adı"

    // Fallback değerler
    const val AVATAR_FALLBACK = "?"
    const val USERNAME_PREFIX = "@"
}

/**
 * UI boyut sabitleri — padding, size, radius gibi değerler tek yerden yönetilir.
 */
object Dimens {
    // Genel
    val ScreenPadding = 16.dp
    val ContentPadding = 14.dp

    // Avatar
    val AvatarSizeSmall = 52.dp
    val AvatarSizeLarge = 96.dp

    // Card
    val CardCornerRadius = 16.dp
    val CardElevation = 1.dp
    val CardHorizontalPadding = 16.dp
    val CardVerticalPadding = 5.dp

    // Arama çubuğu
    val SearchBarCornerRadius = 28.dp
    val SearchBarVerticalPadding = 8.dp

    // Detay ekranı
    val InfoIconSize = 40.dp
    val InfoIconInnerSize = 20.dp
    val InfoIconCornerRadius = 12.dp
    val DividerHeight = 0.5.dp

    // Liste icon boyutu
    val SmallIconSize = 14.dp

    // Error ekranı
    val ErrorIconSize = 64.dp
    val ErrorSectionPadding = 32.dp

    // Divider alpha
    const val DividerAlpha = 0.5f
    const val ErrorIconAlpha = 0.7f
    const val ChevronAlpha = 0.5f
    const val IconContainerAlpha = 0.5f
}

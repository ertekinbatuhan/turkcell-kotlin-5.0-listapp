package com.example.turkcelllistapp.di

import com.example.turkcelllistapp.data.remote.ApiService
import com.example.turkcelllistapp.data.remote.Endpoints
import com.example.turkcelllistapp.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt DI modülü — ağ katmanı ve repository bağımlılıklarını sağlar.
 * Base URL [Endpoints] objesinden alınır.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(api: ApiService): UserRepository =
        UserRepository(api)
}

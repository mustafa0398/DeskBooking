package com.codingschool.deskbooking.service.api

import com.codingschool.deskbooking.service.authentication.AuthenticationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://deskbooking.dev.webundsoehne.com/"

    private val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authenticationService: AuthenticationService by lazy {
        instance.create(AuthenticationService::class.java)
    }
}
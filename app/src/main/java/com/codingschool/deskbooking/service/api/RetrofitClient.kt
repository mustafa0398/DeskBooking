package com.codingschool.deskbooking.service.api

import com.codingschool.deskbooking.service.authentication.AuthenticationService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    var authToken: String? = null

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            if (authToken != null) {
                val newRequest = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(chain.request())
            }
        }
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private const val BASE_URL = "https://deskbooking.dev.webundsoehne.com/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authenticationService: AuthenticationService by lazy {
        instance.create(AuthenticationService::class.java)
    }
}


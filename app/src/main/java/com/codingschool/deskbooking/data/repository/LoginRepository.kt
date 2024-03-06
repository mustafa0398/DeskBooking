package com.codingschool.deskbooking.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.codingschool.deskbooking.service.api.RetrofitClient

class LoginRepository(context: Context) {
    private val applicationContext = context.applicationContext
    private val masterKey = MasterKey.Builder(applicationContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveLoginTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString("accessToken", accessToken)
            putString("refreshToken", refreshToken)
            apply()
        }
    }

    fun getAccessToken(): String? = sharedPreferences.getString("accessToken", null)
}
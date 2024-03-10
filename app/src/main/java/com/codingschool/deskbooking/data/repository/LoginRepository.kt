package com.codingschool.deskbooking.data.repository

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class LoginRepository(context: Context) {
    private val applicationContext = context.applicationContext
    private val context = context
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

    fun clearLoginTokens() {
        sharedPreferences.edit().clear().apply {
            remove("accessToken")
            remove("refreshToken")
            apply()
        }
    }

    fun clearApplicationCache() {
        try {
            val cacheDirectory = context.cacheDir
            val files = cacheDirectory.listFiles()
            if (files != null) {
                for (file in files) {
                    file.deleteRecursively()
                }
            }
        } catch (e: Exception) {
            Log.e("LoginRepository", "Fehler beim Löschen des Cache", e)
        }
    }

    fun getAccessToken(): String? = sharedPreferences.getString("accessToken", null)
}
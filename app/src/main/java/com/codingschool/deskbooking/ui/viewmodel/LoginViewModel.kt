package com.codingschool.deskbooking.ui.viewmodel

import androidx.security.crypto.MasterKey
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import com.codingschool.deskbooking.model.authentication.login.Login
import com.codingschool.deskbooking.model.authentication.login.LoginResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private lateinit var masterKey: MasterKey
    private lateinit var appContext: Context
    val response = MutableLiveData<Result<LoginResponse>>()

    fun init(context: Context) {
        this.appContext = context.applicationContext
        this.masterKey = MasterKey.Builder(appContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.authenticationService.loginUser(Login(email, password))
                if (result.isSuccessful && result.body() != null) {
                    val loginResponse = result.body()!!
                    response.postValue(Result.success(loginResponse))

                    val encryptedSharedPreferences = EncryptedSharedPreferences.create(
                        appContext,
                        "secret_shared_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    )

                    encryptedSharedPreferences.edit().apply {
                        putString("accessToken", loginResponse.token)
                        putString("refreshToken", loginResponse.refresh)
                        apply()
                    }
                } else {
                    val errorMessage = result.errorBody()?.string() ?: "Unbekannter Fehler"
                    response.postValue(Result.failure(Exception("Login fehlgeschlagen: $errorMessage")))
                }
            } catch (e: Exception) {
                response.postValue(Result.failure(e))
            }
        }
    }
}
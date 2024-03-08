package com.codingschool.deskbooking.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.login.Login
import com.codingschool.deskbooking.data.model.dto.login.LoginResponse
import com.codingschool.deskbooking.data.repository.LoginRepository
import com.codingschool.deskbooking.data.repository.UserRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val response = MutableLiveData<Result<LoginResponse>>()
    val isLoggedIn = MutableLiveData<Boolean>()

    // Führt den Login-Vorgang aus und speichert Tokens bei Erfolg.
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.apiService.loginUser(Login(email, password))
                if (result.isSuccessful && result.body() != null) {
                    val loginResponse = result.body()!!
                    response.postValue(Result.success(loginResponse))
                    loginRepository.saveLoginTokens(loginResponse.token, loginResponse.refresh)
                    RetrofitClient.authToken = loginResponse.token
                } else {
                    val errorMessage = result.errorBody()?.string() ?: "Unbekannter Fehler"
                    response.postValue(Result.failure(Exception("Login fehlgeschlagen: $errorMessage")))
                }
            } catch (e: Exception) {
                response.postValue(Result.failure(e))
            }
        }
    }

    // Überprüft, ob ein Zugriffstoken vorhanden ist, was bedeutet, dass der Benutzer eingeloggt ist.
    fun checkLoginStatus() {
        viewModelScope.launch {
            val accessToken = loginRepository.getAccessToken()
            RetrofitClient.authToken = accessToken
            isLoggedIn.postValue(accessToken != null)
        }
    }
}

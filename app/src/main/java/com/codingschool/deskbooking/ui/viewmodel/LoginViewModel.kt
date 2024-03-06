package com.codingschool.deskbooking.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.authentication.login.Login
import com.codingschool.deskbooking.data.model.authentication.login.LoginResponse
import com.codingschool.deskbooking.data.repository.LoginRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch
// ViewModel, das die Login-Logik steuert und mit dem Repository interagiert.
class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    val response = MutableLiveData<Result<LoginResponse>>() // Veröffentlicht das Ergebnis des Login-Vorgangs
    val isLoggedIn = MutableLiveData<Boolean>() // Verfolgt, ob der Benutzer aktuell eingeloggt ist

    // Führt den Login-Vorgang aus und speichert Tokens bei Erfolg.
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.authenticationService.loginUser(Login(email, password))
                if (result.isSuccessful && result.body() != null) {
                    val loginResponse = result.body()!!
                    response.postValue(Result.success(loginResponse))
                    loginRepository.saveLoginTokens(loginResponse.token, loginResponse.refresh)
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
            isLoggedIn.postValue(accessToken != null)
        }
    }
}

package com.codingschool.deskbooking.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.authentication.register.Register
import com.codingschool.deskbooking.data.model.authentication.register.RegisterResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    // Verwaltet die Logik für die Registrierung und kommuniziert mit dem AuthenticationService.
    val response = MutableLiveData<Result<RegisterResponse>>()

    fun register(register: Register) {
        // Startet einen Coroutine-Block für den Netzwerkanruf.
        // Ruft `authenticationService.registerUser(register)` auf und verarbeitet die Antwort.
        viewModelScope.launch {
            try {
                val result = RetrofitClient.authenticationService.registerUser(register)
                if (result.isSuccessful && result.body() != null) {
                    response.postValue(Result.success(result.body()!!))
                } else {
                    val errorMessage = result.errorBody()?.string() ?: "Unbekannter Fehler"
                    response.postValue(Result.failure(Exception("Registrierung fehlgeschlagen: $errorMessage")))
                }
            } catch (e: Exception) {
                response.postValue(Result.failure(e))
            }
        }
    }
}
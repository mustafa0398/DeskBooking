package com.codingschool.deskbooking.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.model.authentication.register.Register
import com.codingschool.deskbooking.model.authentication.register.RegisterResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    val response = MutableLiveData<Result<RegisterResponse>>()

    fun register(register: Register) {
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
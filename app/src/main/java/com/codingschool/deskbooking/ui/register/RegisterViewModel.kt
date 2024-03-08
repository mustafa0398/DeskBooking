package com.codingschool.deskbooking.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.register.Register
import com.codingschool.deskbooking.data.model.dto.register.RegisterResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val response = MutableLiveData<Result<RegisterResponse>>()

    fun register(register: Register) {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.apiService.registerUser(register)
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
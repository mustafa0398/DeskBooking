package com.codingschool.deskbooking.ui.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.register.Register
import com.codingschool.deskbooking.data.model.dto.register.RegisterResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class RegisterViewModel (private val context: Context) : ViewModel() {
    val response = MutableLiveData<Result<RegisterResponse>>()

    fun register(register: Register) {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.apiService.registerUser(register)
                if (result.isSuccessful && result.body() != null) {
                    response.postValue(Result.success(result.body()!!))
                } else {
                    val errorMessage = result.errorBody()?.string() ?: context.getString(R.string.unknown_error_register)
                    response.postValue(Result.failure(Exception(
                        context.getString(
                            R.string.registration_failed,
                            errorMessage
                        ))))
                }
            } catch (e: Exception) {
                response.postValue(Result.failure(e))
            }
        }
    }
}
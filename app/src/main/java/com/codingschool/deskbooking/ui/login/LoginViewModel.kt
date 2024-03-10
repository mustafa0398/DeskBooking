package com.codingschool.deskbooking.ui.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.login.Login
import com.codingschool.deskbooking.data.model.dto.login.LoginResponse
import com.codingschool.deskbooking.data.repository.LoginRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val context: Context
) : ViewModel() {
    val response = MutableLiveData<Result<LoginResponse>>()
    val isLoggedIn = MutableLiveData<Boolean>()

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
                    val errorMessage = result.errorBody()?.string() ?: context.getString(R.string.unknown_error_login)
                    response.postValue(Result.failure(Exception(
                        context.getString(
                            R.string.login_failed,
                            errorMessage
                        ))))
                }
            } catch (e: Exception) {
                response.postValue(Result.failure(e))
            }
        }
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            val accessToken = loginRepository.getAccessToken()
            RetrofitClient.authToken = accessToken
            isLoggedIn.postValue(accessToken != null)
        }
    }


}

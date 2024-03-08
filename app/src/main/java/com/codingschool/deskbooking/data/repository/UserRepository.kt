package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.dto.user.User
import com.codingschool.deskbooking.service.api.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {
    private val _userIdFlow : MutableStateFlow<String?> = MutableStateFlow(null)
    val userIdFlow : StateFlow<String?> = _userIdFlow.asStateFlow()
    suspend fun getUserProfile(): Response<User> {
        return apiService.getUserProfile().also { if(it.isSuccessful){
            _userIdFlow.value = it.body()?.id
        } }
    }
}
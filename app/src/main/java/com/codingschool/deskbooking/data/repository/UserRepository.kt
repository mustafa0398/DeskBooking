package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.authentication.user.User
import com.codingschool.deskbooking.service.authentication.AuthenticationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response

class UserRepository(private val authenticationService: AuthenticationService) {
    private val _userIdFlow : MutableStateFlow<String?> = MutableStateFlow(null)
    val userIdFlow : StateFlow<String?> = _userIdFlow.asStateFlow()
    suspend fun getUserProfile(): Response<User> {
        return authenticationService.getUserProfile().also { if(it.isSuccessful){
            _userIdFlow.value = it.body()?.id
        } }
    }
}
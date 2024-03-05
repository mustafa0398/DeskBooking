package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.authentication.user.User
import com.codingschool.deskbooking.service.authentication.AuthenticationService
class UserRepository(private val authenticationService: AuthenticationService) {
    suspend fun getUserProfile(): User {
        return authenticationService.getUserProfile()
    }
}
package com.codingschool.deskbooking.data.model.dto.login

data class LoginResponse(
    val token: String,
    val refresh: String
)

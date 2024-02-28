package com.codingschool.deskbooking.model.authentication.login

data class LoginResponse(
    val token: String,
    val refresh: String
)

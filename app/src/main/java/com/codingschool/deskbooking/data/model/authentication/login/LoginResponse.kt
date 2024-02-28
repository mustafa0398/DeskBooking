package com.codingschool.deskbooking.data.model.authentication.login

data class LoginResponse(
    val token: String,
    val refresh: String
)

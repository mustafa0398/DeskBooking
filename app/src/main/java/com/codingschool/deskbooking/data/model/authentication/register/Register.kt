package com.codingschool.deskbooking.data.model.authentication.register

data class Register(
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val password: String
)

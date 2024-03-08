package com.codingschool.deskbooking.data.model.dto.user

data class UserUpdate(
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val password: String
)
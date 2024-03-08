package com.codingschool.deskbooking.data.model.dto.register
data class RegisterResponse(
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val id: String,
    val isAdmin: Boolean,
    val createdAt: String,
    val updatedAt: String
)

package com.codingschool.deskbooking.data.model.dto.user

data class User(
    val id: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val isAdmin: Boolean,
    val createdAt: String,
    val updatedAt: String
)

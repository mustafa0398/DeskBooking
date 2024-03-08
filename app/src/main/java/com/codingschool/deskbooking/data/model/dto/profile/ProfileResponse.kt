package com.codingschool.deskbooking.data.model.dto.profile

data class ProfileResponse (
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val id: String,
    val isAdmin: Boolean,
    val createdAt: String,
    val updatedAt: String
)
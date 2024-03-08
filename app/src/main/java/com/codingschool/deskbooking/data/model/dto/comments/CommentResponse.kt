package com.codingschool.deskbooking.data.model.dto.comments

data class CommentResponse(
    val comment: String,
    val id: String,
    val user: User,
    val desk: Desk,
    val commentedAt: String,
    val updatedAt: String
)

data class User(
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val id: String,
    val isAdmin: Boolean,
    val createdAt: String,
    val updatedAt: String
)

data class Desk(
    val label: String,
    val id: String,
    val equipment: List<String>,
    val type: String,
    val row: Int,
    val column: Int,
    val createdAt: String,
    val updatedAt: String
)


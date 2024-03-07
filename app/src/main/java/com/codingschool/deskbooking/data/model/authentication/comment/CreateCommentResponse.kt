package com.codingschool.deskbooking.data.model.authentication.comment

data class CreateCommentResponse(
    val comment: String,
    val id: String,
    val user: String,
    val desk: String,
    val commentedAt: String,
    val updatedAt: String
)

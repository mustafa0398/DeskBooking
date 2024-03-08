package com.codingschool.deskbooking.data.model.dto.desks

data class FixDeskResponseUpdate(
    val id: String,
    val user: String,
    val desk: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val updatedBy: String
)



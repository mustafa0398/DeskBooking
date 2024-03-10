package com.codingschool.deskbooking.data.model.dto.desks

data class CreatingDeskResponse (
    val label: String,
    val office: String,
    val equipment: List<String>,
    val row: Int,
    val column: Int,
    val type: String,
    val fixdesk: String,
    val id: String,
    val createdAt: String,
    val updatedAt: String
)


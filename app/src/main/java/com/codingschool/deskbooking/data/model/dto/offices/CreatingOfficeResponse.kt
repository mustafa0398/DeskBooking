package com.codingschool.deskbooking.data.model.dto.offices

data class CreatingOfficeResponse(
    val name: String,
    val columns: Int,
    val rows: Int,
    val id: String,
    val map: String,
    val createdAt: String,
    val updatedAt: String
)
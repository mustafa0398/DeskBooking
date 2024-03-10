package com.codingschool.deskbooking.data.model.dto.desks

import com.codingschool.deskbooking.data.model.dto.equipment.Equipment

data class CreatingDeskResponse (
    val label: String,
    val office: String,
    val equipment: List<Equipment>,
    val row: Int,
    val column: Int,
    val type: String,
    val fixdesk: String,
    val id: String,
    val createdAt: String,
    val updatedAt: String
)


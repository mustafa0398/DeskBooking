package com.codingschool.deskbooking.data.model.dto.offices

import com.codingschool.deskbooking.data.model.dto.desks.Desk

data class Offices(
    val name: String,
    val columns: Int,
    val rows: Int,
    val id: String,
    val map: String,
    val createdAt: String,
    val updatedAt: String,
    val desks: List<Desk>
)
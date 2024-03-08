package com.codingschool.deskbooking.data.model.dto.desks
import com.codingschool.deskbooking.data.model.dto.comments.User

data class FixDeskResponse(
    val id: String,
    val user: User,
    val desk: DeskForFixDesk,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val updatedBy: UpdateBy
)

data class DeskForFixDesk(
    val label: String,
    val id: String,
    val equipment: List<String>,
    val type: String,
    val row: Int,
    val column: Int,
    val createdAt: String,
    val updatedAt: String
)

data class UpdateBy(
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val id: String,
    val isAdmin: Boolean,
    val createdAt: String,
    val updatedAt: String
)
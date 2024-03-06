package com.codingschool.deskbooking.data.model.authentication.desks

import com.codingschool.deskbooking.data.model.authentication.offices.Offices
import com.codingschool.deskbooking.data.model.authentication.user.User

data class Desk(
    val label: String,
    val id: String,
    val nextBooking: String,
    val fixDesk: FixDesk,
    val type: String,
    val isUserFavourite: Boolean,
    val office: Offices,
    val equipment: List<String>,
    val createdAt: String,
    val updatedAt: String
)

data class FixDesk(
    val id: String,
    val user: User,
    val createdAt: String,
    val status: String,
    val updatedAt: String,
    val updatedBy: User
)
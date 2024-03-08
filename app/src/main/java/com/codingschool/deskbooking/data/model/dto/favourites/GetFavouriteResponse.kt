package com.codingschool.deskbooking.data.model.dto.favourites

import com.codingschool.deskbooking.data.model.authentication.offices.Office


data class GetFavouriteResponse(
    val id: String,
    val user: GetFavouriteUser,
    val desk: GetFavouriteDesk,
    val createdAt: String,
    val updatedAt: String
)

data class GetFavouriteDesk(
    val label: String,
    val id: String,
    val equipment: List<String>,
    val type: String,
    val office: Office,
    val createdAt: String,
    val updatedAt: String
)

data class GetFavouriteUser(
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val id: String,
    val isAdmin: Boolean,
    val createdAt: String,
    val updatedAt: String
)
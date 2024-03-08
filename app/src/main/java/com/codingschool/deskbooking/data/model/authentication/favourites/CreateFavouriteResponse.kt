package com.codingschool.deskbooking.data.model.authentication.favourites

data class CreateFavouriteResponse(
    val id: String,
    val user: String,
    val desk: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

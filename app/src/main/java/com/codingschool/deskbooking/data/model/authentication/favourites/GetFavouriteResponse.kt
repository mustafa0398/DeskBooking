package com.codingschool.deskbooking.data.model.authentication.favourites

import com.codingschool.deskbooking.data.model.authentication.desks.Desk
import com.codingschool.deskbooking.data.model.authentication.user.User

data class GetFavouriteResponse(
    val id: String,
    val user: User,
    val desk: Desk,
    val createdAt: String,
    val updatedAt: String
)
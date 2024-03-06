package com.codingschool.deskbooking.data.model.authentication.bookings

import com.codingschool.deskbooking.data.model.authentication.desks.Desk
import com.codingschool.deskbooking.data.model.authentication.user.User

data class BookingResponse(
    val dateStart: String,
    val dateEnd: String,
    val id: String,
    val user: User,
    val desk: Desk,
    val bookedAt: String
)

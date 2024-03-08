package com.codingschool.deskbooking.data.model.dto.bookings

import com.codingschool.deskbooking.data.model.dto.desks.Desk
import com.codingschool.deskbooking.data.model.dto.user.User

data class BookingResponse(
    val dateStart: String,
    val dateEnd: String,
    val id: String,
    val user: User,
    val desk: Desk,
    val bookedAt: String
)

package com.codingschool.deskbooking.data.model.dto.bookings

data class CreateBooking(
    val dateStart: String,
    val dateEnd: String,
    val desk: String
)
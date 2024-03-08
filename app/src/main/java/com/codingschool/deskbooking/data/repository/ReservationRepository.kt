package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.dto.bookings.BookingResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import retrofit2.Response

class ReservationRepository {
    suspend fun getBookingsFromUser(userId: String): Response<List<BookingResponse>> {
        return RetrofitClient.apiService.getBookingsFromUser(userId)
    }
}

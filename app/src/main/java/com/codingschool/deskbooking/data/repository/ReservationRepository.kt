package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.dto.bookings.BookingResponse
import com.codingschool.deskbooking.service.api.ApiService
import com.codingschool.deskbooking.service.api.RetrofitClient
import retrofit2.Response

class ReservationRepository(private val apiService: ApiService) {
    suspend fun getBookingsFromUser(userId: String): Response<List<BookingResponse>> {
        return apiService.getBookingsFromUser(userId)
    }
}

package com.codingschool.deskbooking.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.authentication.bookings.BookingResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class ReservationViewModel : ViewModel() {

    private val _userBookings = MutableLiveData<List<BookingResponse>>()
    val userBookings: LiveData<List<BookingResponse>> = _userBookings

    fun getUserBookings(userId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.authenticationService.getUserBookings(userId)
                if (response.isSuccessful) {
                    _userBookings.postValue(response.body())
                } else {
                    Log.e("ReservationViewModel", "Failed to fetch user bookings: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Failed to fetch user bookings: ${e.message}")
            }
        }
    }
}

package com.codingschool.deskbooking.ui.reservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.authentication.bookings.BookingResponse
import com.codingschool.deskbooking.data.repository.ReservationRepository
import com.codingschool.deskbooking.data.repository.UserRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class ReservationViewModel : ViewModel() {

    private val reservationRepository = ReservationRepository()
    private val _userBookings = MutableLiveData<List<BookingResponse>>()
    val userBookings: LiveData<List<BookingResponse>> = _userBookings
    private val userRepository: UserRepository by inject(UserRepository::class.java)
    fun getBookingsFromUser() {
        viewModelScope.launch {
            userRepository.userIdFlow.collect { userId ->
                userId?.let {
                    try {
                        val response = reservationRepository.getBookingsFromUser(it)
                        if (response.isSuccessful) {
                            _userBookings.postValue(response.body())
                        } else {
                            Log.e(
                                "ReservationViewModel",
                                "Failed to fetch user bookings: ${response.code()}"
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("ReservationViewModel", "Failed to fetch user bookings: ${e.message}")
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch { if (userRepository.userIdFlow.value.isNullOrBlank()) userRepository.getUserProfile() }
    }
}

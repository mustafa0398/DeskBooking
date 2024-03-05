package com.codingschool.deskbooking.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.authentication.bookings.CreateBooking
import com.codingschool.deskbooking.data.model.authentication.bookings.BookingResponse
import com.codingschool.deskbooking.data.model.authentication.desks.Desk
import com.codingschool.deskbooking.data.repository.DesksRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class DesksViewModel : ViewModel() {

    private val desksRepository = DesksRepository()
    val desksLiveData = MutableLiveData<List<Desk>>()
    val errorMessageLiveData = MutableLiveData<String>()
    private val _bookingResult = MutableLiveData<Result<BookingResponse>>()

    fun getDesksById(id : String) {
        viewModelScope.launch {
            try {
                val desks = desksRepository.getDesksById()
                desksLiveData.postValue(desks.filter { it.office.id == id })
            } catch (e: Exception) {
                errorMessageLiveData.postValue(e.message)
            }
        }
    }



    fun createBooking(deskId: String, startDate: String, endDate: String) {
        val createBooking = CreateBooking(dateStart = startDate, dateEnd = endDate, desk = deskId)
        viewModelScope.launch {
            try {
                val result = RetrofitClient.authenticationService.createBooking(createBooking)
                if (result.isSuccessful) {
                    val bookingResponse = result.body()!!
                } else {
                    Log.e("ViewModel", "Error creating booking: ${result.code()}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Failed to create booking: ${e.message}")
            }
        }
    }

}
package com.codingschool.deskbooking.ui.bookingplan.desks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.authentication.bookings.CreateBooking
import com.codingschool.deskbooking.data.model.authentication.bookings.BookingResponse
import com.codingschool.deskbooking.data.model.authentication.desks.Desk
import com.codingschool.deskbooking.data.model.authentication.equipment.Equipment
import com.codingschool.deskbooking.data.repository.DesksRepository
import com.codingschool.deskbooking.data.repository.EquipmentRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DesksViewModel(private val desksRepository: DesksRepository) : ViewModel() {

    private val equipmentRepository = EquipmentRepository()


    val desksLiveData = MutableLiveData<List<Desk>>()
    val equipmentLiveData = MutableLiveData<List<Equipment>>()
    val errorMessageLiveData = MutableLiveData<String>()
    private val _bookingResult = MutableLiveData<Result<BookingResponse>>()

    private val _equipments = MutableLiveData<List<Equipment>>()
    val equipments: LiveData<List<Equipment>> = _equipments

/*    fun getDesksById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val test = desksRepository.loadAllDesks().filter {
                    it.office.id == id
                }.apply {
                    desksRepository.saveDesks(this)
                    val savedDesk = desksRepository.getSavedDesks()
                    desksLiveData.postValue(savedDesk)
                }
                desksRepository.saveDesks(test)
            } catch (e: Exception) {
                errorMessageLiveData.postValue(e.message)
            }
        }
    }*/

    fun getDesksByOfficeId(id: String) {
        viewModelScope.launch {
            desksRepository.loadAllDesks()
            desksRepository.getSavedDesks().collect { desks ->
                desksLiveData.postValue(desks.filter {
                    it.office.id == id
                })
            }
        }
    }

    fun loadAllDesk() {
        viewModelScope.launch {
            desksRepository.loadAllDesks()
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

    fun getEquipments() {
        viewModelScope.launch {
            try {
                val response = equipmentRepository.getAllEquipments()
                if (response.isSuccessful) {
                    _equipments.postValue(response.body())
                } else {
                    // Handle error response
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}
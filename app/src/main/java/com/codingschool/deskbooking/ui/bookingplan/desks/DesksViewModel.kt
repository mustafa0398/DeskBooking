package com.codingschool.deskbooking.ui.bookingplan.desks

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.bookings.CreateBooking
import com.codingschool.deskbooking.data.model.dto.bookings.BookingResponse
import com.codingschool.deskbooking.data.model.dto.desks.Desk
import com.codingschool.deskbooking.data.model.dto.equipment.Equipment
import com.codingschool.deskbooking.data.repository.DesksRepository
import com.codingschool.deskbooking.data.repository.EquipmentRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import com.codingschool.deskbooking.Event
import com.codingschool.deskbooking.R
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class DesksViewModel(private val desksRepository: DesksRepository, private val context: Context) : ViewModel() {

    private val equipmentRepository = EquipmentRepository()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    val desksLiveData = MutableLiveData<List<Desk>>()
    val equipmentLiveData = MutableLiveData<List<Equipment>>()
    val errorMessageLiveData = MutableLiveData<String>()
    private val _bookingResult = MutableLiveData<Result<BookingResponse>>()

    private val _equipments = MutableLiveData<List<Equipment>>()
    val equipments: LiveData<List<Equipment>> = _equipments

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
                val result = RetrofitClient.apiService.createBooking(createBooking)
                if (result.isSuccessful) {
                    val successMessage = context.getString(R.string.reservation_was_successful)
                    statusMessage.value = Event(successMessage)
                } else {
                    val errorBody = result.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    statusMessage.value = Event(errorMessage)
                }
            } catch (e: Exception) {
                statusMessage.value = Event(e.message ?: context.getString(R.string.unknown_error_create_booking))
            }
        }
    }

    fun getEquipments() {
        viewModelScope.launch {
            try {
                val response = equipmentRepository.getAllEquipments()
                if (response.isSuccessful) {
                    _equipments.postValue(response.body())
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    statusMessage.value = Event(errorMessage)
                } else {
                    //.
                }
            } catch (e: Exception) {
                //.
            }
        }
    }

    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.getString("message")
        } catch (e: JSONException) {
            "Error parsing message"
        }
    }
}
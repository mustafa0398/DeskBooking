package com.codingschool.deskbooking.ui.bookingplan.offices

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.offices.Offices
import com.codingschool.deskbooking.data.model.authentication.offices.Office
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class OfficesViewModel : ViewModel() {

    private val _office = MutableLiveData<List<Office>>()
    val office: LiveData<List<Office>> get() = _office
    fun getAllOffices() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.authenticationService.getAllOffices()
                if (response.isSuccessful) {
                    _office.postValue(response.body())
                } else {
                    Log.e("ViewModel", "Error fetching offices: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Failed to fetch offices: ${e.message}")
            }
        }
    }
}

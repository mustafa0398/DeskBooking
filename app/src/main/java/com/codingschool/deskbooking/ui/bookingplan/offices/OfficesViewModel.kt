package com.codingschool.deskbooking.ui.bookingplan.offices

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.authentication.offices.Offices
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class OfficesViewModel : ViewModel() {

    private val _offices = MutableLiveData<List<Offices>>()
    val offices: LiveData<List<Offices>> get() = _offices
    fun getAllOffices() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.authenticationService.getAllOffices()
                if (response.isSuccessful) {
                    _offices.postValue(response.body())
                } else {
                    Log.e("ViewModel", "Error fetching offices: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Failed to fetch offices: ${e.message}")
            }
        }
    }
}

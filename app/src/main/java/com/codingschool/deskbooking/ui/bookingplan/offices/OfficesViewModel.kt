package com.codingschool.deskbooking.ui.bookingplan.offices

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.authentication.offices.Office
import com.codingschool.deskbooking.data.model.dto.profile.ProfileResponse
import com.codingschool.deskbooking.data.repository.OfficesRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import com.codingschool.deskbooking.service.api.RetrofitClient.apiService
import kotlinx.coroutines.launch

class OfficesViewModel(private val officesRepository: OfficesRepository) : ViewModel() {
    val userProfile = MutableLiveData<ProfileResponse?>()
    val isAdmin = MutableLiveData<Boolean?>()
    private val _office = MutableLiveData<List<Office>>()
    val office: LiveData<List<Office>> get() = _office
    fun getAllOffices() {
        viewModelScope.launch {
            try {
                val response = apiService.getAllOffices()
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

    fun fetchUserProfile() {
        viewModelScope.launch {
            try {
                val result = officesRepository.getUserProfile()
                userProfile.value = result.getOrThrow()
                isAdmin.value = result.getOrThrow().isAdmin
            } catch (exception: Exception) {
                Log.e("ProfileViewModel", "Fehler beim Abrufen des Profils", exception)
                userProfile.value = null
                isAdmin.value = null
            }
        }
    }
}

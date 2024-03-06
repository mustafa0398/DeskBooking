package com.codingschool.deskbooking.ui.bookingplan.equipment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.repository.EquipmentRepository
import kotlinx.coroutines.launch

class EquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {

    private val _equipments = MutableLiveData<Map<String, String>?>()
    fun getAllEquipments() {
        viewModelScope.launch {
            try {
                val response = repository.getAllEquipments()
                if (response.isSuccessful) {
                    val equipmentList = response.body()
                    val equipmentMap = equipmentList?.associate { it.id to it.name }
                    _equipments.value = equipmentMap
                    Log.d("EquipmentViewModel", "Equipment list loaded successfully: $response")
                } else {
                    Log.e("EquipmentViewModel", "Failed to load equipment list: response was null")
                }
            } catch (e: Exception) {
                Log.e("EquipmentViewModel", "Error loading equipment list", e)
            }
        }
    }
}

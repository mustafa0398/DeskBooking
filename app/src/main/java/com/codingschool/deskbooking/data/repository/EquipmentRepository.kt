package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.dto.equipment.Equipment
import com.codingschool.deskbooking.service.api.RetrofitClient
import retrofit2.Response

class EquipmentRepository() {
    suspend fun getAllEquipments(): Response<List<Equipment>> {
        return RetrofitClient.apiService.getAllEquipments()
    }
}
package com.codingschool.deskbooking.data.repository

import android.content.Context
import android.util.Log
import com.codingschool.deskbooking.data.model.dto.profile.ProfileResponse
import com.codingschool.deskbooking.service.api.ApiService

class ProfileRepository(private val apiService: ApiService) {
    suspend fun getUserProfile(): Result<ProfileResponse> {
        return try {
            val response = apiService.getProfile()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Fehler beim Abrufen des Profils"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
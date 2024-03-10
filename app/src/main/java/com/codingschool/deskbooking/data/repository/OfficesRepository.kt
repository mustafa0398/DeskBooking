package com.codingschool.deskbooking.data.repository

import android.content.Context
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.profile.ProfileResponse
import com.codingschool.deskbooking.service.api.RetrofitClient.apiService

class OfficesRepository (private val context: Context) {

    suspend fun getUserProfile(): Result<ProfileResponse> {
        return try {
            val response = apiService.getProfile()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(context.getString(R.string.error_retrieving_profile)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
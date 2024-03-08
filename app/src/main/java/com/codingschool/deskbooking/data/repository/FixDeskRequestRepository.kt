package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.dto.desks.FixDeskRequestUpdate
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponseUpdate
import com.codingschool.deskbooking.service.api.ApiService

class FixDeskRequestRepository(private val apiService: ApiService) {
    suspend fun getAllFixDeskRequests(): Result<List<FixDeskResponse>> {
        return try {
            val response = apiService.getAllFixDeskRequests()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val error = if(response.errorBody() != null) {
                    response.errorBody()!!.string()
                } else {
                    "Unbekannter Fehler"
                }
                Result.failure(ApiException(response.code(), error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateFixDeskRequest(updateRequest: FixDeskRequestUpdate): Result<FixDeskResponseUpdate> {
        return try {
            val response = apiService.updateFixDeskRequest(updateRequest)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val error = if(response.errorBody() != null) {
                    response.errorBody()!!.string()
                } else {
                    "Unbekannter Fehler"
                }
                Result.failure(ApiException(response.code(), error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class ApiException(val statusCode: Int, errorMessage: String) : Exception(errorMessage)

package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.dto.comments.CommentResponse
import com.codingschool.deskbooking.data.model.dto.desks.CreatingDesk
import com.codingschool.deskbooking.data.model.dto.desks.CreatingDeskResponse
import com.codingschool.deskbooking.data.model.dto.offices.CreatingOffice
import com.codingschool.deskbooking.data.model.dto.offices.CreatingOfficeResponse
import com.codingschool.deskbooking.service.api.ApiService


class AdminRepository(private val apiService: ApiService) {

    suspend fun getAllComments(page: Int): Result<List<CommentResponse>> {
        return try {
            val response = apiService.getAllComments(page)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error fetching comments"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createOffice(creatingOffice: CreatingOffice): Result<CreatingOfficeResponse> {
        return try {
            val response = apiService.createOffice(creatingOffice)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(RuntimeException("API call failed with response: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createDesk(creatingDesk: CreatingDesk): Result<CreatingDeskResponse> {
        return try {
            val response = apiService.createDesk(creatingDesk)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(RuntimeException("API call failed with response: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
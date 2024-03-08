package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.dto.comments.CommentResponse
import com.codingschool.deskbooking.service.api.ApiService

class AdminCommentRepository(private val service: ApiService) {

    suspend fun getAllComments(page: Int): Result<List<CommentResponse>> {
        return try {
            val response = service.getAllComments(page)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error fetching comments"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
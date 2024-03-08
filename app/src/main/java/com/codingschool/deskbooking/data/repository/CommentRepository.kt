package com.codingschool.deskbooking.data.repository


import com.codingschool.deskbooking.data.model.authentication.comment.CreateCommentRequest
import com.codingschool.deskbooking.data.model.authentication.comment.CreateCommentResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import retrofit2.Response

class CommentRepository(private val service: ApiService) {
    suspend fun createComment(createCommentRequest: CreateCommentRequest): Response<CreateCommentResponse> {
        return service.createComment(createCommentRequest)
    }

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
import com.codingschool.deskbooking.data.model.dto.comments.CommentResponse
import com.codingschool.deskbooking.service.api.ApiService

class CommentRepository(private val service: ApiService) {
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
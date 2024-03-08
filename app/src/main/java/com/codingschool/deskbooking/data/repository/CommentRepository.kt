package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.authentication.comment.CreateCommentRequest
import com.codingschool.deskbooking.data.model.authentication.comment.CreateCommentResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import retrofit2.Response

class CommentRepository {
    suspend fun createComment(createCommentRequest: CreateCommentRequest): Response<CreateCommentResponse> {
        return RetrofitClient.authenticationService.createComment(createCommentRequest)
    }
}
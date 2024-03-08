package com.codingschool.deskbooking.data.repository


import com.codingschool.deskbooking.data.model.authentication.comment.CreateCommentRequest
import com.codingschool.deskbooking.data.model.authentication.comment.CreateCommentResponse
import com.codingschool.deskbooking.data.model.dto.comments.CommentResponse
import com.codingschool.deskbooking.service.api.ApiService
import com.codingschool.deskbooking.service.api.RetrofitClient

import retrofit2.Response

class CommentRepository() {
    suspend fun createComment(createCommentRequest: CreateCommentRequest): Response<CreateCommentResponse> {
        return RetrofitClient.apiService.createComment(createCommentRequest)
    }


}

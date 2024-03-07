package com.codingschool.deskbooking.data.model.authentication.comment

data class CreateCommentRequest(
    val comment: String,
    val desk: String
)

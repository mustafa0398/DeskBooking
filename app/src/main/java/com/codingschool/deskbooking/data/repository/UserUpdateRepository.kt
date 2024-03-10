package com.codingschool.deskbooking.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.user.UserUpdate
import com.codingschool.deskbooking.service.api.ApiService

class UserUpdateRepository(private val userService: ApiService, private val context: Context) {
    @SuppressLint("StringFormatMatches")
    suspend fun updateUser(id: String, userUpdate: UserUpdate): Result<UserUpdate> {
        return try {
            val response = userService.updateUser(id, userUpdate)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(
                    context.getString(
                        R.string.error_updating_user,
                        response.code()
                    )))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
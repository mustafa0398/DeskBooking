package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.dto.user.UserUpdate
import com.codingschool.deskbooking.service.api.ApiService

class UserUpdateRepository(private val userService: ApiService) {
    suspend fun updateUser(id: String, userUpdate: UserUpdate): Result<UserUpdate> {
        return try {
            val response = userService.updateUser(id, userUpdate)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Fehler beim Aktualisieren des Benutzers: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
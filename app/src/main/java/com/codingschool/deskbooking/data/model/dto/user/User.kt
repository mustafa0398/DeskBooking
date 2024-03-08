package com.codingschool.deskbooking.data.model.dto.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings


data class User(
    val id: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val isAdmin: Boolean,
    val createdAt: String,
    val updatedAt: String
)
@Entity(tableName = "user")
data class UserRoom(
    @PrimaryKey
    val userId: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val department: String,
    val isAdmin: Boolean,
    val createdAt: String,
    val updatedAt: String
)

fun User.toUserRoom() = UserRoom(userId = id, firstname, lastname, email, department, isAdmin, createdAt, updatedAt)

fun UserRoom.toUser() = User(id = userId, firstname, lastname, email, department, isAdmin, createdAt, updatedAt)
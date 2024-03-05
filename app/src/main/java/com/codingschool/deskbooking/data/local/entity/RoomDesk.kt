package com.codingschool.deskbooking.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "desks")
data class RoomDesk(
    @PrimaryKey val id: String,
    val name: String,
    val description: String
)

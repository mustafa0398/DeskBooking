package com.codingschool.deskbooking.data.model.authentication.offices

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Office(
    val name: String,
    val columns: Int,
    val rows: Int,
    val id: String,
    val map: String,
    val createdAt: String,
    val updatedAt: String,
)

@Entity(tableName = "offices")
data class OfficeRoom(
    val name: String,
    val columns: Int,
    val rows: Int,
    @PrimaryKey
    val officeId: String,
    val map: String,
    val createdAt: String,
    val updatedAt: String,
)

fun Office.toOfficeRoom() = OfficeRoom(name, columns, rows, officeId = id, map, createdAt, updatedAt)
fun OfficeRoom.toOffice() = Office(name, columns, rows, id = officeId, map, createdAt, updatedAt)
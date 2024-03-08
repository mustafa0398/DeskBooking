package com.codingschool.deskbooking.data.model.dto.desks

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings
import com.codingschool.deskbooking.data.model.authentication.offices.Office
import com.codingschool.deskbooking.data.model.dto.user.User
import com.codingschool.deskbooking.data.model.dto.user.UserRoom
import com.codingschool.deskbooking.data.model.dto.user.toUser
import com.codingschool.deskbooking.data.model.dto.user.toUserRoom

data class Desk(
    val label: String,
    val id: String,
    val nextBooking: String?,
    val fixDesk: FixDesk?,
    val type: String,
    val isUserFavourite: Boolean,
    val office: Office,
    val equipment: List<String>,
    val createdAt: String,
    val updatedAt: String
)

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = "desks")
data class DeskRoom(
    val label: String,
    @PrimaryKey val deskId: String,
    val nextBooking: String?,
    val fixDesk: FixDesk?,
    val type: String,
    val isUserFavourite: Boolean,
    val office: Office,
    val equipment: List<String> = emptyList(),
    val createdAt: String,
    val updatedAt: String
)

data class FixDesk(
    val id: String,
    val user: User,
    val createdAt: String,
    val status: String,
    val updatedAt: String,
    val updatedBy: User
)
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = "fixDesk")
data class FixDeskRoom(
    @PrimaryKey
    val fixDeskId: String,
    @Embedded(prefix = "user")
    val user: UserRoom,
    val createdAt: String,
    val status: String,
    val updatedAt: String,
    @Embedded(prefix = "updatedBy")
    val updatedBy: UserRoom
)

fun Desk.toDeskRoom() = DeskRoom(label = label, deskId = id, nextBooking, fixDesk, type, isUserFavourite, office, equipment, createdAt, updatedAt)
fun DeskRoom.toDesk() = Desk(label = label, id = deskId, nextBooking, fixDesk, type, isUserFavourite, office, equipment, createdAt, updatedAt)

fun FixDesk.toFixDeskRoom() = FixDeskRoom(fixDeskId = id, user.toUserRoom(), createdAt, status, updatedAt, updatedBy.toUserRoom())
fun FixDeskRoom.toFixDesk() = FixDesk(id = fixDeskId, user.toUser(), createdAt, status, updatedAt, updatedBy.toUser())
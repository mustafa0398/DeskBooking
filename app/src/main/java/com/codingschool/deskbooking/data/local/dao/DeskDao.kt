package com.codingschool.deskbooking.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codingschool.deskbooking.data.model.dto.desks.Desk

@Dao
interface DeskDao {
    @Insert
    suspend fun insertDesk(desk: Desk)

    @Query("SELECT * FROM desks")
    suspend fun getAllDesks(): List<Desk>
}

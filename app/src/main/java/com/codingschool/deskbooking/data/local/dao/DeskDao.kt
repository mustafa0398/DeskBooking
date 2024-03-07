package com.codingschool.deskbooking.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codingschool.deskbooking.data.model.authentication.desks.Desk
import com.codingschool.deskbooking.data.model.authentication.desks.DeskRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface DeskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesk(desk: List<DeskRoom>)

    @Query("SELECT * FROM desks")
    fun getAllDesks(): Flow<List<DeskRoom>>
}

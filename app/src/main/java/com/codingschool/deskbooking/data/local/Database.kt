package com.codingschool.deskbooking.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingschool.deskbooking.data.local.dao.DeskDao
import com.codingschool.deskbooking.data.model.authentication.desks.Desk

@Database(entities = [Desk::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun deskDao(): DeskDao
}

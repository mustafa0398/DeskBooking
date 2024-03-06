package com.codingschool.deskbooking.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingschool.deskbooking.data.local.dao.DeskDao
import com.codingschool.deskbooking.data.local.entity.RoomDesk

@Database(entities = [RoomDesk::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun deskDao(): DeskDao
}

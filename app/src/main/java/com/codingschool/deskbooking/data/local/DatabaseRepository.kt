package com.codingschool.deskbooking.data.local

import android.content.Context
import androidx.room.Room
import com.codingschool.deskbooking.data.local.dao.DeskDao

object DatabaseRepository {

    private lateinit var database: Database
    fun init(context: Context) {
        database = Room
            .databaseBuilder(
                context,
                Database::class.java,
                "database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }
    val deskDao: DeskDao get() = database.deskDao()

}
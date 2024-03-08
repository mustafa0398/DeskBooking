package com.codingschool.deskbooking.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codingschool.deskbooking.data.local.dao.DeskDao
import com.codingschool.deskbooking.data.model.authentication.offices.OfficeRoom
import com.codingschool.deskbooking.data.model.dto.desks.DeskRoom
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskRoom
import com.codingschool.deskbooking.data.model.dto.user.UserRoom


@Database(entities = [DeskRoom::class, UserRoom::class, OfficeRoom::class, FixDeskRoom::class], version = 1, exportSchema = false)
@TypeConverters(ObjectConverter::class)
abstract class DeskBookingDatabase : RoomDatabase() {
    abstract fun deskDao(): DeskDao
    companion object {
        @Volatile
        private var INSTANCE: DeskBookingDatabase? = null

        fun getDatabase(context: Context): DeskBookingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DeskBookingDatabase::class.java,
                    "DeskBookingDatabase"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


}



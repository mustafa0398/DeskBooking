package com.codingschool.deskbooking.data.module

import androidx.room.Room
import com.codingschool.deskbooking.data.local.dao.DeskDao
import com.codingschool.deskbooking.data.local.database.DeskBookingDatabase
import com.codingschool.deskbooking.data.repository.DesksRepository
import com.codingschool.deskbooking.data.repository.DesksRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val dataModule = module {
    single { DeskBookingDatabase.getDatabase(androidContext()) }
    single<DesksRepository>{
        DesksRepositoryImpl((get<DeskBookingDatabase>(DeskBookingDatabase::class).deskDao()))
    }
/*    single {
        Room.databaseBuilder(
            androidApplication(),
            DeskBookingDatabase::class.java,
            "DeskBookingDataBase"
        )
            .build()
    }
    single<DeskDao>{
        val database = get<DeskBookingDatabase>()
        database.deskDao()
    }*/

/*    single<DesksRepository>{
        DesksRepositoryImpl(get())
    }*/
}

package com.codingschool.deskbooking

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codingschool.deskbooking.data.module.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.codingschool.deskbooking.di.appModule
import com.codingschool.deskbooking.di.adminModule
import com.codingschool.deskbooking.di.desksModule
import com.codingschool.deskbooking.di.favouriteModule
import com.codingschool.deskbooking.di.officesModule
import com.codingschool.deskbooking.di.profileModule
import com.codingschool.deskbooking.di.registerModule
import com.codingschool.deskbooking.di.reservationModule
import com.jakewharton.threetenabp.AndroidThreeTen

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@MyApplication)
            modules(
                dataModule,
                appModule,
                registerModule,
                officesModule,
                desksModule,
                reservationModule,
                favouriteModule,
                adminModule,
                profileModule
            )

        }
    }
}


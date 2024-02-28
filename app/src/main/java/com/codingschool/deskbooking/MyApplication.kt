package com.codingschool.deskbooking

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.codingschool.deskbooking.di.appModule
import com.codingschool.deskbooking.di.registerModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule, registerModule)
        }
    }
}
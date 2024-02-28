package com.codingschool.deskbooking.di

import org.koin.dsl.module
import com.codingschool.deskbooking.data.repository.LoginRepository
import com.codingschool.deskbooking.ui.viewmodel.LoginViewModel
import com.codingschool.deskbooking.ui.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single { LoginRepository(get()) }
    viewModel { LoginViewModel(get()) }
}

val registerModule = module {
    viewModel { RegisterViewModel() }
}
package com.codingschool.deskbooking.di

import com.codingschool.deskbooking.data.repository.LoginRepository
import com.codingschool.deskbooking.data.repository.UserRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import com.codingschool.deskbooking.service.authentication.AuthenticationService
import com.codingschool.deskbooking.ui.viewmodel.DesksViewModel
import com.codingschool.deskbooking.ui.viewmodel.LoginViewModel
import com.codingschool.deskbooking.ui.viewmodel.OfficesViewModel
import com.codingschool.deskbooking.ui.viewmodel.RegisterViewModel
import com.codingschool.deskbooking.ui.viewmodel.ReservationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<AuthenticationService> { RetrofitClient.authenticationService }
    single { LoginRepository(get()) }
    single { UserRepository(get()) }
    viewModel { LoginViewModel(get(), get()) }
}

val registerModule = module {
    viewModel { RegisterViewModel() }
}

val officesModule = module {
    viewModel { OfficesViewModel() }
}

val desksModule = module {
    viewModel { DesksViewModel() }
}

val reservationModule = module {
    viewModel { ReservationViewModel() }
}
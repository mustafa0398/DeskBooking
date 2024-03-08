package com.codingschool.deskbooking.di

import com.codingschool.deskbooking.data.repository.FavouriteRepository
import com.codingschool.deskbooking.data.repository.LoginRepository
import com.codingschool.deskbooking.data.repository.UserRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import com.codingschool.deskbooking.service.authentication.AuthenticationService
import com.codingschool.deskbooking.ui.bookingplan.desks.DesksViewModel
import com.codingschool.deskbooking.ui.login.LoginViewModel
import com.codingschool.deskbooking.ui.bookingplan.offices.OfficesViewModel
import com.codingschool.deskbooking.ui.favourite.FavouriteViewModel
import com.codingschool.deskbooking.ui.register.RegisterViewModel
import com.codingschool.deskbooking.ui.reservation.ReservationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<AuthenticationService> { RetrofitClient.authenticationService }
    single { LoginRepository(get()) }
    single { UserRepository(get()) }
    viewModel { LoginViewModel(get()) }
}

val registerModule = module {
    viewModel { RegisterViewModel() }
}

val officesModule = module {
    viewModel { OfficesViewModel() }
}

val desksModule = module {
    viewModel { DesksViewModel(desksRepository = get()) }

}

val reservationModule = module {
    viewModel { ReservationViewModel() }
}

val favouriteModule = module{
    single { FavouriteRepository() }
    viewModel { FavouriteViewModel(get()) }
}
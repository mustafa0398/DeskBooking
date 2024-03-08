package com.codingschool.deskbooking.di

import com.codingschool.deskbooking.data.repository.AdminCommentRepository
import com.codingschool.deskbooking.data.repository.FixDeskRequestRepository
import com.codingschool.deskbooking.data.repository.FavouriteRepository
import com.codingschool.deskbooking.data.repository.LoginRepository
import com.codingschool.deskbooking.data.repository.ProfileRepository
import com.codingschool.deskbooking.data.repository.UserRepository
import com.codingschool.deskbooking.data.repository.UserUpdateRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import com.codingschool.deskbooking.service.api.ApiService
import com.codingschool.deskbooking.ui.administration.AdminViewModel
import com.codingschool.deskbooking.ui.bookingplan.desks.DesksViewModel
import com.codingschool.deskbooking.ui.login.LoginViewModel
import com.codingschool.deskbooking.ui.bookingplan.offices.OfficesViewModel
import com.codingschool.deskbooking.ui.profile.ProfileViewModel
import com.codingschool.deskbooking.ui.favourite.FavouriteViewModel
import com.codingschool.deskbooking.ui.register.RegisterViewModel
import com.codingschool.deskbooking.ui.reservation.ReservationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<ApiService> { RetrofitClient.apiService }
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
    viewModel { ReservationViewModel(get(), get(), get(), get()) }
}

val adminModule = module {
    single { AdminCommentRepository(get()) }
    single { FixDeskRequestRepository(get()) }
    viewModel { AdminViewModel(get(), get()) }
}

val profileModule = module {
    single { ProfileRepository(get()) }
    single { LoginRepository(get())}
    single { UserUpdateRepository(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
}

val favouriteModule = module {
    single { ProfileRepository(get()) }
    single { FavouriteRepository(get(), get()) }
    viewModel { FavouriteViewModel(get()) }
}
package com.codingschool.deskbooking.service.authentication

import com.codingschool.deskbooking.data.model.authentication.bookings.CreateBooking
import com.codingschool.deskbooking.data.model.authentication.bookings.BookingResponse
import com.codingschool.deskbooking.data.model.authentication.desks.Desk
import com.codingschool.deskbooking.data.model.authentication.equipment.Equipment
import com.codingschool.deskbooking.data.model.authentication.login.Login
import com.codingschool.deskbooking.data.model.authentication.login.LoginResponse
import com.codingschool.deskbooking.data.model.authentication.register.Register
import com.codingschool.deskbooking.data.model.authentication.register.RegisterResponse
import com.codingschool.deskbooking.data.model.authentication.offices.Offices
import com.codingschool.deskbooking.data.model.authentication.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthenticationService {

    @POST("api/users/register")
    suspend fun registerUser(
        @Body register: Register
    ): Response<RegisterResponse>

    @POST("api/users/login")
    suspend fun loginUser(
        @Body login: Login
    ): Response<LoginResponse>

    @GET("api/offices")
    suspend fun getAllOffices(): Response<List<Offices>>
    @GET("api/offices/{id}")
    suspend fun getOfficeById(@Path("id") id: String): Response<Offices>
    @GET("api/desks")
    suspend fun getAllDesks(): Response<List<Desk>>
    @GET("api/desks/{id}")
    suspend fun getDesksById(@Path("id") id: String): Response<List<Desk>>
    @GET("api/users/profile")
    suspend fun getUserProfile(): User
    @GET("api/equipments")
    suspend fun getAllEquipments(): Response<List<Equipment>>

    @GET("api/departments")
    suspend fun getAllDepartments(): Response<Map<String, String>>

    @POST("api/bookings")
    suspend fun createBooking(
        @Body createBooking: CreateBooking
    ): Response<BookingResponse>

    @GET("/api/bookings/user/{id}")
    suspend fun getUserBookings(@Path("id") userId: String): Response<List<BookingResponse>>
}

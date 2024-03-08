package com.codingschool.deskbooking.service.api

import com.codingschool.deskbooking.data.model.dto.bookings.CreateBooking
import com.codingschool.deskbooking.data.model.dto.bookings.BookingResponse
import com.codingschool.deskbooking.data.model.dto.comments.CommentResponse
import com.codingschool.deskbooking.data.model.dto.desks.Desk
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskRequestUpdate
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponseUpdate
import com.codingschool.deskbooking.data.model.dto.equipment.Equipment
import com.codingschool.deskbooking.data.model.dto.login.Login
import com.codingschool.deskbooking.data.model.dto.login.LoginResponse
import com.codingschool.deskbooking.data.model.dto.register.Register
import com.codingschool.deskbooking.data.model.dto.register.RegisterResponse
import com.codingschool.deskbooking.data.model.dto.offices.Offices
import com.codingschool.deskbooking.data.model.dto.profile.ProfileResponse
import com.codingschool.deskbooking.data.model.dto.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

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
    suspend fun getDesksById(): Response<List<Desk>>

    @GET("api/users/profile")
    suspend fun getUserProfile(): Response<User>

    @GET("api/equipments")
    suspend fun getAllEquipments(): Response<List<Equipment>>

    @GET("api/departments")
    suspend fun getAllDepartments(): Response<Map<String, String>>

    @POST("api/bookings")
    suspend fun createBooking(
        @Body createBooking: CreateBooking
    ): Response<BookingResponse>

    @GET("/api/bookings/user/{id}")
    suspend fun getBookingsFromUser(
        @Path("id") userId: String
    ): Response<List<BookingResponse>>

    @GET("/api/comments")
    suspend fun getAllComments(
        @Query("page") page: Int
    ): Response<List<CommentResponse>>

    @GET("/api/admin/fix-desk-requests")
    suspend fun getAllFixDeskRequests(
    ): Response<List<FixDeskResponse>>

    @PUT("/api/admin/fix-desk-requests")
    suspend fun updateFixDeskRequest(
        @Body updateRequest: FixDeskRequestUpdate
    ): Response<FixDeskResponseUpdate>

    @GET("/api/users/profile")
    suspend fun getProfile(
    ): Response<ProfileResponse>
}

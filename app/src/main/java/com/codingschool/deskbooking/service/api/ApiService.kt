package com.codingschool.deskbooking.service.api

import com.codingschool.deskbooking.data.model.authentication.comment.CreateCommentRequest
import com.codingschool.deskbooking.data.model.authentication.comment.CreateCommentResponse
import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteRequest
import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteResponse
import com.codingschool.deskbooking.data.model.authentication.favourites.GetFavouriteResponse
import com.codingschool.deskbooking.data.model.authentication.offices.Office
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
import com.codingschool.deskbooking.data.model.dto.profile.ProfileResponse
import com.codingschool.deskbooking.data.model.dto.user.User
import com.codingschool.deskbooking.data.model.dto.user.UserUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    suspend fun getAllOffices(): Response<List<Office>>

    @GET("api/offices/{id}")
    suspend fun getOfficeById(@Path("id") id: String): Response<Office>

    @GET("api/desks")
    suspend fun getDesksById(): Response<List<Desk>>

    @GET("api/desks")
    suspend fun getAllDesks(): Response<List<Desk>>

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

    @POST("api/comments")
    suspend fun createComment(
        @Body createCommentRequest: CreateCommentRequest
    ): Response<CreateCommentResponse>


    @GET("/api/bookings/user/{id}")
    suspend fun getBookingsFromUser(
        @Path("id") userId: String
    ): Response<List<BookingResponse>>

    @POST("api/favourites")
    suspend fun createFavourite(
        @Body createFavouriteRequest: CreateFavouriteRequest
    ): Response<CreateFavouriteResponse>
    @GET("api/favourites/user/{userId}")
    suspend fun getUserFavorites(
        @Path("userId") userId: String
    ): Response<List<GetFavouriteResponse>>

    @DELETE("api/favourites/{id}")
    suspend fun deleteFavourite(
        @Path("id") id: String
    )


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

    @PUT("/api/users/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body userUpdate: UserUpdate
    ): Response<UserUpdate>

}

package com.codingschool.deskbooking.service.authentication

import com.codingschool.deskbooking.data.model.authentication.login.Login
import com.codingschool.deskbooking.data.model.authentication.login.LoginResponse
import com.codingschool.deskbooking.data.model.authentication.register.Register
import com.codingschool.deskbooking.data.model.authentication.register.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST("api/users/register")
    suspend fun registerUser(
        @Body register: Register
    ): Response<RegisterResponse>

    @POST("api/users/login")
    suspend fun loginUser(
        @Body login: Login
    ): Response<LoginResponse>

}
package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteRequest
import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteResponse
import com.codingschool.deskbooking.data.model.authentication.favourites.GetFavouriteResponse
import com.codingschool.deskbooking.service.api.RetrofitClient
import retrofit2.Response

class FavouriteRepository {
    suspend fun createFavourite(createFavouriteRequest: CreateFavouriteRequest): Response<CreateFavouriteResponse> {
        return RetrofitClient.apiService.createFavourite(createFavouriteRequest)
    }
    suspend fun getUserFavorites(userId: String): Response<List<GetFavouriteResponse>> {
        val favorites = RetrofitClient.apiService.getUserFavorites(userId)
        return Response.success(favorites)
    }
}
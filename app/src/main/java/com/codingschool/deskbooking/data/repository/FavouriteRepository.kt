package com.codingschool.deskbooking.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteRequest
import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteResponse
import com.codingschool.deskbooking.data.model.dto.favourites.GetFavouriteResponse
import com.codingschool.deskbooking.service.api.ApiService
import retrofit2.Response

class FavouriteRepository(private val profileRepository: ProfileRepository, private val apiService: ApiService, private val context: Context) {

    suspend fun createFavourite(createFavouriteRequest: CreateFavouriteRequest): Response<CreateFavouriteResponse> {
        return apiService.createFavourite(createFavouriteRequest)
    }
    suspend fun getUserFavorites(): Result<List<GetFavouriteResponse>> {
        return try {
            val userProfileResult = profileRepository.getUserProfile()

            userProfileResult.fold(
                onSuccess = { userProfile ->
                    val userId = userProfile.id
                    val response = apiService.getUserFavorites(userId)
                    if (response.isSuccessful && response.body() != null) {
                        Result.success(response.body()!!)
                    } else {
                        Result.failure(Exception(
                            context.getString(
                                R.string.error_retrieving_favorites,
                                response.message()
                            )))
                    }
                },
                onFailure = {
                    Result.failure(Exception(context.getString(R.string.error_retrieving_user_profile)))
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @SuppressLint("StringFormatMatches")
    suspend fun deleteFavourite(id: String): Result<Unit> {
        return try {
            val response = apiService.deleteFavourite(id)
            if (response.isSuccessful && response.code() == 204) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(
                    context.getString(
                        R.string.error_deleting_favorite_http,
                        response.code()
                    )))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

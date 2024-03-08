package com.codingschool.deskbooking.data.repository

import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteRequest
import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteResponse
import com.codingschool.deskbooking.data.model.dto.favourites.GetFavouriteResponse
import com.codingschool.deskbooking.service.api.ApiService
import retrofit2.Response

class FavouriteRepository(private val profileRepository: ProfileRepository, private val apiService: ApiService) {

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
                        Result.failure(Exception("Fehler beim Abrufen der Favoriten: ${response.message()}"))
                    }
                },
                onFailure = {
                    Result.failure(Exception("Fehler beim Abrufen des Benutzerprofils"))
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteFavourite(id: String): Result<Unit> {
        return try {
            val response = apiService.deleteFavourite(id)
            if (response.isSuccessful && response.code() == 204) { // Verwendung von code als Eigenschaft
                Result.success(Unit)
            } else {
                Result.failure(Exception("Fehler beim Löschen des Favoriten: HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

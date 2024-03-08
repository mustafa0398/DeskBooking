package com.codingschool.deskbooking.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.authentication.favourites.GetFavouriteResponse
import com.codingschool.deskbooking.data.repository.FavouriteRepository
import com.codingschool.deskbooking.service.api.RetrofitClient
import kotlinx.coroutines.launch

class FavouriteViewModel(private val favouriteRepository : FavouriteRepository) : ViewModel() {

    private val _favourites = MutableLiveData<List<GetFavouriteResponse>>()
    val favourites: LiveData<List<GetFavouriteResponse>> = _favourites

    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean> = _deleteResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getUserFavourites(userId: String) {
        viewModelScope.launch {
            try {
                val response = favouriteRepository.getUserFavorites(userId)
                if (response.isSuccessful) {
                    _favourites.value = response.body()
                } else {
                    _error.value = "Failed to fetch favourites: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error fetching favourites: ${e.message}"
            }
        }
    }

    fun deleteFavourite(id: String) {
        viewModelScope.launch {
            RetrofitClient.authenticationService.deleteFavourite(id)
        }
    }
}
package com.codingschool.deskbooking.ui.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.favourites.GetFavouriteResponse
import com.codingschool.deskbooking.data.repository.FavouriteRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(private val favouriteRepository: FavouriteRepository) : ViewModel() {

    private val _favourites = MutableLiveData<List<GetFavouriteResponse>>()
    val favourites: LiveData<List<GetFavouriteResponse>> = _favourites

    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean> = _deleteResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getUserFavourites() {
        Log.d("FavouriteViewModel", "Starting API call to fetch favourites")
        viewModelScope.launch {
            val result = favouriteRepository.getUserFavorites()
            result.fold(
                onSuccess = { favourites ->
                    Log.d("FavouriteViewModel", "Favourites fetched successfully")
                    _favourites.value = favourites
                },
                onFailure = { exception ->
                    Log.e("FavouriteViewModel", "Error fetching favourites: ${exception.message}", exception)
                    _error.value = "Error fetching favourites: ${exception.message}"
                }
            )
        }
    }

    fun deleteFavourite(id: String) {
        viewModelScope.launch {
            favouriteRepository.deleteFavourite(id).fold(
                onSuccess = {
                    Log.d("FavouriteViewModel", "Favourite successfully deleted")
                    _deleteResult.value = true
                    // Rufen Sie hier getUserFavourites auf, um die Liste zu aktualisieren
                    getUserFavourites()
                },
                onFailure = { exception ->
                    Log.e("FavouriteViewModel", "Error deleting favourite: ${exception.message}", exception)
                    _error.value = "Error deleting favourite: ${exception.message}"
                }
            )
        }
    }
}
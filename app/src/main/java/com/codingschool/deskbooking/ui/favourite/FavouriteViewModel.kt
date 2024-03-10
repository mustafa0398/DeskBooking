package com.codingschool.deskbooking.ui.favourite

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.favourites.GetFavouriteResponse
import com.codingschool.deskbooking.data.repository.FavouriteRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(private val favouriteRepository: FavouriteRepository, private val context: Context) : ViewModel() {

    private val _favourites = MutableLiveData<List<GetFavouriteResponse>>()
    val favourites: LiveData<List<GetFavouriteResponse>> = _favourites

    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean> = _deleteResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getUserFavourites() {
        viewModelScope.launch {
            val result = favouriteRepository.getUserFavorites()
            result.fold(
                onSuccess = { favourites ->
                    _favourites.value = favourites
                },
                onFailure = { exception ->
                    _error.value =
                        context.getString(R.string.error_fetching_favourites, exception.message)
                }
            )
        }
    }

    fun deleteFavourite(id: String) {
        viewModelScope.launch {
            favouriteRepository.deleteFavourite(id).fold(
                onSuccess = {
                    _deleteResult.value = true
                    getUserFavourites()
                },
                onFailure = { exception ->
                    _error.value =
                        context.getString(R.string.error_deleting_favourite, exception.message)
                }
            )
        }
    }
}
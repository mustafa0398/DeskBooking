package com.codingschool.deskbooking.ui.reservation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingschool.deskbooking.data.model.dto.bookings.BookingResponse
import com.codingschool.deskbooking.data.model.authentication.comment.CreateCommentRequest
import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteRequest
import com.codingschool.deskbooking.data.repository.CommentRepository
import com.codingschool.deskbooking.data.repository.FavouriteRepository
import com.codingschool.deskbooking.data.repository.ReservationRepository
import com.codingschool.deskbooking.data.repository.UserRepository
import kotlinx.coroutines.launch



class ReservationViewModel(
    private val userRepository: UserRepository,
    private val favouriteRepository: FavouriteRepository,
    private val commentRepository: CommentRepository,
    private val reservationRepository: ReservationRepository
) : ViewModel() {

    private val _userBookings = MutableLiveData<List<BookingResponse>>()
    val userBookings: LiveData<List<BookingResponse>> = _userBookings

    fun getBookingsFromUser() {
        viewModelScope.launch {
            userRepository.userIdFlow.collect { userId ->
                userId?.let {
                    try {
                        val response = reservationRepository.getBookingsFromUser(it)
                        if (response.isSuccessful) {
                            _userBookings.postValue(response.body())
                        } else {
                            //
                        }
                    } catch (e: Exception) {
                        //
                    }
                }
            }
        }
    }

    fun createComment(comment: String, deskId: String) {
        viewModelScope.launch {
            try {
                val createCommentRequest = CreateCommentRequest(comment, deskId)
                val response = commentRepository.createComment(createCommentRequest)
                if (response.isSuccessful) {
                    //
                } else {
                    //
                }
            } catch (e: Exception) {
                //
            }
        }
    }

    fun createFavourite(desk: String) {
        viewModelScope.launch {
            try {
                val createFavouriteRequest = CreateFavouriteRequest(desk)
                val response = favouriteRepository.createFavourite(createFavouriteRequest)
                if (response.isSuccessful) {
                    //
                } else {
                    //
                }
            } catch (e: Exception) {
                //
            }
        }
    }

    init {
        viewModelScope.launch { if (userRepository.userIdFlow.value.isNullOrBlank()) userRepository.getUserProfile() }
    }
}

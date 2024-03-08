package com.codingschool.deskbooking.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.authentication.bookings.BookingResponse
import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteResponse

class ReservationFragment : Fragment(), ReservationAdapter.CommentClickListener, ReservationAdapter.FavoriteClickListener {

    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var reservationAdapter: ReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_reservation, container, false)

        reservationViewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)

        val recyclerView: RecyclerView = root.findViewById(R.id.rvReservation)
        reservationAdapter = ReservationAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = reservationAdapter

        reservationViewModel.userBookings.observe(viewLifecycleOwner, Observer { bookings ->
            reservationAdapter.submitList(bookings)
        })

        reservationViewModel.getBookingsFromUser()

        return root
    }
    override fun onCommentSendClicked(booking: BookingResponse, comment: String) {
        reservationViewModel.createComment(comment, booking.desk.id)
    }
    override fun onFavoriteClicked(favourite: CreateFavouriteResponse) {
        reservationViewModel.createFavourite(favourite.desk)
    }
}

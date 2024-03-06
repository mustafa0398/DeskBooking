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

class ReservationFragment : Fragment() {

    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var reservationAdapter: ReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_reservation, container, false)

        reservationViewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)

        val recyclerView: RecyclerView = root.findViewById(R.id.rvReservation)
        reservationAdapter = ReservationAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = reservationAdapter

        reservationViewModel.userBookings.observe(viewLifecycleOwner, Observer { bookings ->
            reservationAdapter.submitList(bookings)
        })

        reservationViewModel.getBookingsFromUser()

        return root
    }
}
// DesksFragment.kt
package com.codingschool.deskbooking.ui.bookingplan.desks

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
import com.codingschool.deskbooking.data.model.authentication.bookings.CreateBooking
import com.codingschool.deskbooking.data.model.authentication.desks.Desk
import com.codingschool.deskbooking.ui.viewmodel.DesksViewModel

class DesksFragment : Fragment(), DesksAdapter.BookingClickListener {

    private lateinit var desksViewModel: DesksViewModel
    private lateinit var desksAdapter: DesksAdapter
    private lateinit var id : String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_desks, container, false)

        desksViewModel = ViewModelProvider(this).get(DesksViewModel::class.java)

        val recyclerView: RecyclerView = root.findViewById(R.id.rvDesks)
        desksAdapter = DesksAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = desksAdapter

        id = arguments?.getString("id").toString()

        desksViewModel.desksLiveData.observe(viewLifecycleOwner, Observer { desks ->
            desks?.let {
                desksAdapter.submitList(it)
            }
        })
        desksViewModel.getDesksById(id)
        return root
    }

    override fun onBookingClick(createBooking: CreateBooking) {
        val deskId = createBooking.desk
        val startDate = "2024-03-04T12:00:00Z"
        val endDate = "2024-03-04T13:00:00Z"

        desksViewModel.createBooking(deskId, startDate, endDate)
    }

}

package com.codingschool.deskbooking.ui.bookingplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.ui.administration.AdminAdapter

class BookingPlanFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookingplan, container, false)

        val commentList = listOf("Office 1", "Office 2", "Office 3", "Office 4")

        val recyclerView: RecyclerView = view.findViewById(R.id.rvBookingPlan)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val bookingPlanAdapter = BookingPlanAdapter(commentList)
        recyclerView.adapter = bookingPlanAdapter

        return view
    }
}
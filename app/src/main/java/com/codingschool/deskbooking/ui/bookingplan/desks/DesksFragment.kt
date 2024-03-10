package com.codingschool.deskbooking.ui.bookingplan.desks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import org.koin.android.ext.android.inject
import com.codingschool.deskbooking.data.model.dto.bookings.CreateBooking
import com.google.android.material.snackbar.Snackbar

class DesksFragment : Fragment(), DesksAdapter.BookingClickListener {

    private val desksViewModel: DesksViewModel by inject()
    private lateinit var desksAdapter: DesksAdapter
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        desksViewModel.loadAllDesk()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_desks, container, false)

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

        desksViewModel.equipmentLiveData.observe(viewLifecycleOwner, Observer { equipment ->
            equipment?.let {
                desksAdapter.updateEquipment(it)
            }
        })
        desksViewModel.getDesksByOfficeId(id)
        desksViewModel.getEquipments()
        myMessage()
        return root
    }

    override fun onBookingClick(createBooking: CreateBooking) {
        val deskId = createBooking.desk
        val startDate = createBooking.dateStart
        val endDate = createBooking.dateEnd
        desksViewModel.createBooking(
            deskId,
            startDate,
            endDate
        )
    }

    fun myMessage() {
        desksViewModel.message.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}



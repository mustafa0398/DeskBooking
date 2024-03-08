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
import com.codingschool.deskbooking.data.model.authentication.equipment.Equipment
import org.koin.android.ext.android.inject
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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
        return root
    }

    override fun onBookingClick(createBooking: CreateBooking) {
        val deskId = createBooking.desk
        val startDate = LocalDateTime.now()
        val endDate = LocalDateTime.now()
        desksViewModel.createBooking(
            deskId,
            startDate.format(DateTimeFormatter.ISO_DATE_TIME),
            endDate.format(DateTimeFormatter.ISO_DATE_TIME)
        )
    }

}

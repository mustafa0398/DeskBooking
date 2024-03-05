package com.codingschool.deskbooking.ui.bookingplan.offices

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.ui.viewmodel.OfficesViewModel

class OfficesFragment : Fragment() {

    private lateinit var officesViewModel: OfficesViewModel
    private lateinit var officesAdapter: OfficesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_offices, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.officesRecyclerView)

        officesAdapter = OfficesAdapter(requireContext(), emptyList())

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = officesAdapter
        }

        officesViewModel = ViewModelProvider(this).get(OfficesViewModel::class.java)

        officesViewModel.offices.observe(viewLifecycleOwner) { offices ->
            Log.d("LiveData", "New offices received: ${offices.size}")
            officesAdapter.updateData(offices)
        }

        officesViewModel.getAllOffices()

        return view
    }
}
package com.codingschool.deskbooking.ui.bookingplan.offices

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.ui.profile.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class OfficesFragment : Fragment() {

    private val officesViewModel: OfficesViewModel by inject()
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

        officesViewModel.office.observe(viewLifecycleOwner) { offices ->
            officesAdapter.updateData(offices)
        }

        officesViewModel.getAllOffices()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        officesViewModel.fetchUserProfile()
        officesViewModel.isAdmin.observe(viewLifecycleOwner) { isAdmin ->
            val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNav)
            val adminItem = bottomNavView.menu.findItem(R.id.administrationFragment)

            adminItem.isVisible = isAdmin == true

            bottomNavView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.administrationFragment -> {
                        if (isAdmin == true) {
                            findNavController().navigate(menuItem.itemId)
                            true
                        } else {
                            Snackbar.make(
                                view,
                                getString(R.string.no_access),
                                Snackbar.LENGTH_LONG
                            ).show()
                            false
                        }
                    }

                    else -> {
                        findNavController().navigate(menuItem.itemId)
                        true
                    }
                }
            }
        }
    }


}

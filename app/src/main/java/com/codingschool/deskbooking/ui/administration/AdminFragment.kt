package com.codingschool.deskbooking.ui.administration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse
import com.codingschool.deskbooking.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminFragment : Fragment(), AdminActionListener {
    private val adminViewModel: AdminViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView // Deklariert die RecyclerView hier
    private val profileViewModel: ProfileViewModel by viewModel() // Fügt das ProfileViewModel hinzu, falls benötigt

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvAdmin)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adminAdapter = AdminAdapter(listOf(), this)
        recyclerView.adapter = adminAdapter

        loadAndDisplayData(adminAdapter)
        observeUpdateResult()

        profileViewModel.isLoggedOut.observe(viewLifecycleOwner) { isLoggedOut ->
            if (isLoggedOut) {
                resetAdminData() // Zurücksetzen der Daten beim Logout
            }
        }
    }

    private fun loadAndDisplayData(adminAdapter: AdminAdapter) {
        adminViewModel.loadComments(0)

        adminViewModel.comments.observe(viewLifecycleOwner) { commentResult ->
            commentResult.fold(onSuccess = { comments ->
                val itemsToShow = mutableListOf<Any>()

                if (comments.isNotEmpty()) {
                    itemsToShow.add("Kommentare")
                    itemsToShow.addAll(comments)
                }

                adminViewModel.loadFixDeskRequests()

                adminViewModel.fixDeskRequests.observe(viewLifecycleOwner) { fixDeskRequestResult ->
                    fixDeskRequestResult.fold(onSuccess = { fixDeskRequests ->
                        if (fixDeskRequests.isNotEmpty()) {
                            itemsToShow.add("FixDesk-Anfragen")
                            itemsToShow.addAll(fixDeskRequests)
                        }

                        adminAdapter.updateItems(itemsToShow)

                    }, onFailure = { error ->
                        Toast.makeText(context, "Fehler: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
                    })
                }

            }, onFailure = { error ->
                Toast.makeText(context, "Fehler: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
            })
        }


    }

    private fun observeUpdateResult() {
        adminViewModel.updateResult.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }, onFailure = { error ->
                Toast.makeText(context, "Fehler: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun onFixDeskRequestConfirm(fixDeskRequest: FixDeskResponse) {
        adminViewModel.confirmAndDeclineFixDeskRequest(fixDeskRequest.id, "approved")
    }

    override fun onFixDeskRequestDecline(fixDeskRequest: FixDeskResponse) {
        adminViewModel.confirmAndDeclineFixDeskRequest(fixDeskRequest.id, "rejected")
    }

    fun resetAdminData() {
        val adminAdapter = recyclerView.adapter as? AdminAdapter // Verwendet die Klassenvariable recyclerView
        adminAdapter?.updateItems(emptyList()) // Leert den Adapter, wenn der Nutzer sich ausloggt
    }

}
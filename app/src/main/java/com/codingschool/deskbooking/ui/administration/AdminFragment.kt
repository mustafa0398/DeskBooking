package com.codingschool.deskbooking.ui.administration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse
import com.codingschool.deskbooking.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminFragment : Fragment(), AdminActionListener {
    private val adminViewModel: AdminViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvAdmin)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = AdminAdapter(mutableListOf(), this@AdminFragment)

        setupDataObservation()

        adminViewModel.updateResult.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                // Hier könnten Sie dann die Liste neu laden oder die spezifischen Daten aktualisieren,
                // je nachdem, wie Ihre Datenverwaltung eingerichtet ist.
                adminViewModel.loadComments(0)  // Lade Kommentare erneut
                adminViewModel.loadFixDeskRequests()  // Lade FixDesk-Anfragen erneut
            }, onFailure = { error ->
                Toast.makeText(context, "Fehler: ${error.message}", Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun setupDataObservation() {
        adminViewModel.loadComments(0)  // Lade Kommentare
        adminViewModel.loadFixDeskRequests()  // Lade FixDesk-Anfragen

        // Beobachte Kommentare und FixDesk-Anfragen, um UI zu aktualisieren
        adminViewModel.comments.observe(viewLifecycleOwner) { result ->
            updateAdminData()
        }

        adminViewModel.fixDeskRequests.observe(viewLifecycleOwner) { result ->
            updateAdminData()
        }

        profileViewModel.isLoggedOut.observe(viewLifecycleOwner) { isLoggedOut ->
            if (isLoggedOut) resetAdminData()  // Zurücksetzen der Daten beim Logout
        }

        adminViewModel.updateResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
                .onFailure { error ->
                    Toast.makeText(
                        context,
                        "Fehler: ${error.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun updateAdminData() {
        val itemsToShow = mutableListOf<Any>()

        adminViewModel.comments.value?.onSuccess { comments ->
            if (comments.isNotEmpty()) {
                itemsToShow.add("Kommentare")
                itemsToShow.addAll(comments)
            }
        }

        adminViewModel.fixDeskRequests.value?.onSuccess { fixDeskRequests ->
            if (fixDeskRequests.isNotEmpty()) {
                itemsToShow.add("FixDesk-Anfragen")
                itemsToShow.addAll(fixDeskRequests)
            }
        }

        (recyclerView.adapter as? AdminAdapter)?.updateItems(itemsToShow)
    }

    override fun onFixDeskRequestConfirm(fixDeskRequest: FixDeskResponse) {
        adminViewModel.confirmAndDeclineFixDeskRequest(fixDeskRequest.id, "approved")
    }

    override fun onFixDeskRequestDecline(fixDeskRequest: FixDeskResponse) {
        adminViewModel.confirmAndDeclineFixDeskRequest(fixDeskRequest.id, "rejected")
    }

    fun resetAdminData() {
        (recyclerView.adapter as? AdminAdapter)?.updateItems(mutableListOf())
    }
}

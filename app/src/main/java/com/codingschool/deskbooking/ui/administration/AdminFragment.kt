package com.codingschool.deskbooking.ui.administration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.desks.CreatingDesk
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse
import com.codingschool.deskbooking.data.model.dto.offices.CreatingOffice
import com.codingschool.deskbooking.ui.profile.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminFragment : Fragment(), AdminActionListener {
    private val adminViewModel: AdminViewModel by viewModel()
    private lateinit var recyclerView: RecyclerView
    private val profileViewModel: ProfileViewModel by viewModel()
    private var createDeskDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCreateRoomAndTable: Button = view.findViewById(R.id.btnCreateRoomAndTable)
        recyclerView = view.findViewById(R.id.rvAdmin)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = AdminAdapter(mutableListOf(),requireContext(), this@AdminFragment)

        btnCreateRoomAndTable.setOnClickListener {
            showCreateRoomDialog()
        }

        setupDataObservation()

        adminViewModel.updateResult.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                adminViewModel.loadComments(0)
                adminViewModel.loadFixDeskRequests()
            }, onFailure = { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            })
        }

        adminViewModel.officeCreationResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { office ->
                    Toast.makeText(requireContext(), "Office successfully created: ${office.name}", Toast.LENGTH_LONG).show()
                    val officeResponse = adminViewModel.officeCreationResult.value?.getOrNull()
                    if (officeResponse != null) {
                        showCreateDeskDialog(officeResponse.id)
                    }
                },
                onFailure = { error ->
                    Toast.makeText(requireContext(), "Error creating new office: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            )
        }

        adminViewModel.deskCreationResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { desk ->
                    createDeskDialog?.dismiss()
                    view?.let { v ->
                        Snackbar.make(v, "Desk successfully created: ${desk.label}", Snackbar.LENGTH_LONG).show()
                    }
                },
                onFailure = { error ->
                    view?.let { v ->
                        Snackbar.make(v, "Error creating new desk: ${error.localizedMessage}", Snackbar.LENGTH_LONG).show()
                    }
                }
            )
        }
    }

    private fun setupDataObservation() {
        adminViewModel.loadComments(0)
        adminViewModel.loadFixDeskRequests()

        adminViewModel.comments.observe(viewLifecycleOwner) {
            updateAdminData()
        }

        adminViewModel.fixDeskRequests.observe(viewLifecycleOwner) {
            updateAdminData()
        }

        profileViewModel.isLoggedOut.observe(viewLifecycleOwner) { isLoggedOut ->
            if (isLoggedOut) resetAdminData()
        }

        adminViewModel.updateResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
                .onFailure { error -> Toast.makeText(context, "Error: ${error.localizedMessage}", Toast.LENGTH_LONG).show() }
        }
    }

    private fun updateAdminData() {
        val itemsToShow = mutableListOf<Any>()

        adminViewModel.comments.value?.onSuccess { comments ->
            if (comments.isNotEmpty()) {
                itemsToShow.add(getString(R.string.all_comments))
                itemsToShow.addAll(comments)
            }
        }

        adminViewModel.fixDeskRequests.value?.onSuccess { fixDeskRequests ->
            if (fixDeskRequests.isNotEmpty()) {
                itemsToShow.add(getString(R.string.fixdesk_requests))
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

    private fun resetAdminData() {
        (recyclerView.adapter as? AdminAdapter)?.updateItems(mutableListOf())
    }

    private fun showCreateRoomDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.create_new_office))

        val inputView = layoutInflater.inflate(R.layout.dialog_create_office, null)
        builder.setView(inputView)

        val etRoomName = inputView.findViewById<EditText>(R.id.etOfficeName)
        val etRoomColumns = inputView.findViewById<EditText>(R.id.etRoomColumns)
        val etRoomRows = inputView.findViewById<EditText>(R.id.etRoomRows)

        builder.setPositiveButton(getString(R.string.admin_create)) { dialog, _ ->
            val name = etRoomName.text.toString().trim()
            val columns = etRoomColumns.text.toString().toIntOrNull()
            val rows = etRoomRows.text.toString().toIntOrNull()

            if (name.isEmpty() || columns == null || rows == null || columns <= 0 || rows <= 0) {
                Toast.makeText(requireContext(),
                    getString(R.string.please_fill_out_all_fields_correctly), Toast.LENGTH_LONG).show()
                dialog.dismiss()
                showCreateRoomDialog()
            } else {
                val creatingOffice = CreatingOffice(name, columns, rows)
                adminViewModel.createOffice(creatingOffice)
            }
        }


        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->

        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showCreateDeskDialog(officeId: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.create_new_desk))

        val inputView = layoutInflater.inflate(R.layout.dialog_create_desk, null)
        builder.setView(inputView)

        val etDeskLabel = inputView.findViewById<EditText>(R.id.etDeskLabel)

        val cbHDMI = inputView.findViewById<CheckBox>(R.id.cbHDMI)
        val cbUSBC = inputView.findViewById<CheckBox>(R.id.cbUSBC)
        val cbHeightAdjustable = inputView.findViewById<CheckBox>(R.id.cbHeightAdjustable)
        val cbLaptopDock = inputView.findViewById<CheckBox>(R.id.cbLaptopDock)
        val cbMonitor = inputView.findViewById<CheckBox>(R.id.cbMonitor)
        val cbMonitor2 = inputView.findViewById<CheckBox>(R.id.cbMonitor2)

        builder.setPositiveButton(getString(R.string.admin_create)) { _, _ ->
            val label = etDeskLabel.text.toString().trim()
            val equipment = mutableListOf<String>().apply {
                if (cbHDMI.isChecked) add("HDMI")
                if (cbUSBC.isChecked) add("USB C")
                if (cbHeightAdjustable.isChecked) add("Height Adjustable")
                if (cbLaptopDock.isChecked) add("Laptop Dock")
                if (cbMonitor.isChecked) add("Monitor")
                if (cbMonitor2.isChecked) add("Monitor 2")
            }

            if (label.isNotEmpty()) {
                val creatingDesk = CreatingDesk(label, officeId, equipment)
                adminViewModel.createDesk(creatingDesk)
            } else {
                Toast.makeText(requireContext(), getString(R.string.please_fill_out_all_fields_correctly), Toast.LENGTH_LONG).show()
            }
        }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        createDeskDialog = builder.create()
        createDeskDialog?.show()
    }

}

package com.codingschool.deskbooking.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.user.UserUpdate
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()
    private var currentDepartment: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val etFirstName = view.findViewById<EditText>(R.id.etRegisterName)
        val etLastName = view.findViewById<EditText>(R.id.etRegisterLastName)
        val etEmail = view.findViewById<EditText>(R.id.etRegisterEmail)
        val etPassword = view.findViewById<EditText>(R.id.etRegisterPassword)
        val logoutButton = view.findViewById<Button>(R.id.btnLogout)
        val updateButton = view.findViewById<Button>(R.id.btnUpdateProfile)

        logoutButton.setOnClickListener {
            resetProfileUI()
            profileViewModel.logoutUser()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        updateButton.setOnClickListener {
            val department = currentDepartment ?: "Ihr Standardabteilungsname"
            val userUpdate = UserUpdate(
                firstname = etFirstName.text.toString(),
                lastname = etLastName.text.toString(),
                email = etEmail.text.toString(),
                department = department,
                password = etPassword.text.toString()
            )
            updateUserProfile(userUpdate)
        }

        profileViewModel.fetchUserProfile()
        observeUserProfile()
    }

    private fun observeUserProfile() {
        profileViewModel.userProfile.observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                view?.findViewById<EditText>(R.id.etRegisterName)?.setText(profile.firstname)
            }
            if (profile != null) {
                view?.findViewById<EditText>(R.id.etRegisterLastName)?.setText(profile.lastname)
            }
            if (profile != null) {
                view?.findViewById<EditText>(R.id.etRegisterEmail)?.setText(profile.email)
            }
            if (profile != null) {
                currentDepartment = profile.department
            }

        }
    }

    private fun resetProfileUI() {
        view?.findViewById<EditText>(R.id.etRegisterName)?.setText("")
        view?.findViewById<EditText>(R.id.etRegisterLastName)?.setText("")
        view?.findViewById<EditText>(R.id.etRegisterEmail)?.setText("")
    }

    private fun updateUserProfile(userUpdate: UserUpdate) {

        val userId = profileViewModel.userProfile.value?.id ?: return
        lifecycleScope.launch {
            val result = profileViewModel.updateUser(userId, userUpdate)
            if (result.isSuccess) {
                Toast.makeText(context, "Benutzerdaten erfolgreich aktualisiert", Toast.LENGTH_SHORT).show()
                profileViewModel.fetchUserProfile()
            } else {
                Toast.makeText(context, "Fehler beim Aktualisieren der Benutzerdaten: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

}
package com.codingschool.deskbooking.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.codingschool.deskbooking.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton = view.findViewById<Button>(R.id.btnLogout)
        logoutButton.setOnClickListener {
            resetProfileUI()
            profileViewModel.logoutUser()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
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
        }
    }

    private fun resetProfileUI() {
        view?.findViewById<EditText>(R.id.etRegisterName)?.setText("")
        view?.findViewById<EditText>(R.id.etRegisterLastName)?.setText("")
        view?.findViewById<EditText>(R.id.etRegisterEmail)?.setText("")
    }
}
package com.codingschool.deskbooking.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.ui.profile.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var etLoginEmail: EditText
    private lateinit var etLoginPassword: EditText
    private val viewModel: LoginViewModel by viewModel()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etLoginEmail = view.findViewById(R.id.etLoginEmail)
        etLoginPassword = view.findViewById(R.id.etLoginPassword)

        val signUpButton: TextView = view.findViewById(R.id.tvSignUpButton)
        signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val loginButton: TextView = view.findViewById(R.id.btn_login)
        loginButton.setOnClickListener {
            val email = etLoginEmail.text.toString().trim()
            val password = etLoginPassword.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                showSnackbar(view, getString(R.string.error_fill_all_fields))
            }
        }

        viewModel.response.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = {
                profileViewModel.fetchUserProfile()
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            }, onFailure = {
                showSnackbar(view, getString(R.string.login_failure))
            })
        }
    }


    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}

package com.codingschool.deskbooking.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.codingschool.deskbooking.R
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.codingschool.deskbooking.data.model.authentication.register.Register
import com.codingschool.deskbooking.ui.viewmodel.RegisterViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var spinner: Spinner
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        etFirstName = view.findViewById(R.id.etRegisterName)
        etLastName = view.findViewById(R.id.etRegisterLastName)
        etEmail = view.findViewById(R.id.etRegisterEmail)
        etPassword = view.findViewById(R.id.etRegisterPassword)
        etConfirmPassword = view.findViewById(R.id.etRegisterConfirm)

        view.findViewById<MaterialButton>(R.id.btn_login).setOnClickListener {
            attemptRegistration()
        }

        spinner = view.findViewById(R.id.spinnerRegisterDepartment)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.department_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        registerViewModel.response.observe(viewLifecycleOwner, Observer { result ->
            result.fold(
                onSuccess = {
                    showSnackbar(view, "Registration successful!")
                    findNavController().popBackStack()
                },
                onFailure = { e ->
                    when {
                        e.message?.contains("400") == true -> {
                            showSnackbar(view, "Input data invalid. Please check your details and try again.")
                        }
                        e.message?.contains("409") == true -> {
                            showSnackbar(view, "A user with this email already exists.")
                        }
                        else -> {
                            showSnackbar(view, e.message ?: "Unknown error occurred")
                        }
                    }
                }
            )
        })

        return view
    }

    private fun attemptRegistration() {
        val selectedDepartment = spinner.selectedItem.toString()
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showSnackbar(requireView(), getString(R.string.error_fill_all_fields))
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showSnackbar(requireView(), getString(R.string.error_invalid_email))
            return
        }

        if (password == confirmPassword) {
            val register = Register(firstName, lastName, email, selectedDepartment, password)
            registerViewModel.register(register)
        } else {
            showSnackbar(requireView(), getString(R.string.error_passwords_do_not_match))
        }
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}
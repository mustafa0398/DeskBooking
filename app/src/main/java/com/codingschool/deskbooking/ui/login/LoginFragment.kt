package com.codingschool.deskbooking.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codingschool.deskbooking.R
class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signUpButton: TextView = view.findViewById(R.id.tvSignUpButton)
        signUpButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        val loginButton: TextView = view.findViewById(R.id.btn_login)
        loginButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
        }
    }
}
package com.codingschool.deskbooking.ui.administration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R

class AdminFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin, container, false)

        val commentList = listOf("User 1", "User 2", "User 3", "User 4")

        val recyclerView: RecyclerView = view.findViewById(R.id.rvAdmin)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adminAdapter = AdminAdapter(commentList)
        recyclerView.adapter = adminAdapter

        return view
    }
}
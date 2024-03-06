package com.codingschool.deskbooking.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        val deskList = listOf("Desk 1", "Desk 2", "Desk 3", "Desk 4")

        val recyclerView: RecyclerView = view.findViewById(R.id.rvFavorites)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val favoritesAdapter = FavoritesAdapter(deskList)
        recyclerView.adapter = favoritesAdapter

        return view
    }
}
package com.codingschool.deskbooking.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R

class FavoritesAdapter(private val deskList: List<String>) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDesk: TextView = itemView.findViewById(R.id.tvDesk)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val btnReserve: Button = itemView.findViewById(R.id.btnReserve)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorites, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deskTitle = deskList[position]
        holder.tvDesk.text = deskTitle
        holder.tvDescription.text = "Description of $deskTitle"

        holder.btnReserve.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return deskList.size
    }
}

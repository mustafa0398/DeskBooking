package com.codingschool.deskbooking.ui.administration

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R

class AdminAdapter(private val adminData: List<String>) :
    RecyclerView.Adapter<AdminAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUser: TextView = itemView.findViewById(R.id.tvUser)
        val tvComment: TextView = itemView.findViewById(R.id.tvComment)
        val btnAccept: Button = itemView.findViewById(R.id.btnAccept)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val adminData = adminData[position]
        holder.tvUser.text = adminData
        holder.tvComment.text = "Comment of $adminData:"

        holder.btnAccept.setOnClickListener {}
    }

    override fun getItemCount(): Int {
        return adminData.size
    }
}

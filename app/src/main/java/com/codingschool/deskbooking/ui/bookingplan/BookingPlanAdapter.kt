package com.codingschool.deskbooking.ui.bookingplan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R

class BookingPlanAdapter(private val planData: List<String>) : RecyclerView.Adapter<BookingPlanAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOffice: TextView = itemView.findViewById(R.id.tvOffice)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bookingplan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val planData = planData[position]
        holder.tvOffice.text = "Office of ${planData}"
    }


    override fun getItemCount(): Int {
        return planData.size
    }
}

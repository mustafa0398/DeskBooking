package com.codingschool.deskbooking.ui.bookingplan.offices

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.data.model.authentication.offices.Office
import com.codingschool.deskbooking.R

class OfficesAdapter(
    private val context: Context,
    private var officeList: List<Office>
) :
    RecyclerView.Adapter<OfficesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOfficeName: TextView = itemView.findViewById(R.id.tvOfficeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_offices, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val office = officeList[position]
        holder.tvOfficeName.text = office.name

        holder.tvOfficeName.setOnClickListener {
            val bundle = bundleOf(
                "id" to office.id
            )
            it.findNavController()
                .navigate(R.id.action_bookingplanFragment_to_desksFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return officeList.size
    }

    fun updateData(newOffices: List<Office>) {
        officeList = newOffices
        notifyDataSetChanged()
    }
}
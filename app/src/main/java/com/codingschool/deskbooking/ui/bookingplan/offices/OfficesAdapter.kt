package com.codingschool.deskbooking.ui.bookingplan.offices

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codingschool.deskbooking.data.model.authentication.offices.Offices
import com.codingschool.deskbooking.R

class OfficesAdapter(
    private val context: Context,
    private var officesList: List<Offices>
) :
    RecyclerView.Adapter<OfficesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOfficeName: TextView = itemView.findViewById(R.id.tvOfficeName)
        val ivOfficeMap: ImageView = itemView.findViewById(R.id.ivOfficeMap)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_offices, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val office = officesList[position]
        holder.tvOfficeName.text = office.name
        val baseUrl = "https://deskbooking.dev.webundsoehne.com/"
        val ImageUrl = office.map
        val fullImageUrl = baseUrl + ImageUrl

        loadAndDisplayImage(fullImageUrl, holder.ivOfficeMap)

        holder.ivOfficeMap.setOnClickListener {
                val bundle = bundleOf(
                    "id" to office.id
                )
            it.findNavController().navigate(R.id.action_bookingplanFragment_to_desksFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return officesList.size
    }

    private fun loadAndDisplayImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.baseline_error_outline_24)
            .into(imageView)
    }

    fun updateData(newOffices: List<Offices>) {
        Log.d("Adapter", "Updating data with ${newOffices.size} offices")
        officesList = newOffices
        notifyDataSetChanged()
    }
}
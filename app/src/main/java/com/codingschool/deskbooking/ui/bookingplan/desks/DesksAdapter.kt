// DesksAdapter.kt
package com.codingschool.deskbooking.ui.bookingplan.desks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.authentication.bookings.CreateBooking
import com.codingschool.deskbooking.data.model.authentication.desks.Desk

class DesksAdapter(private val bookingClickListener: BookingClickListener) : ListAdapter<Desk, DesksAdapter.DeskViewHolder>(DeskDiffCallback()) {

    interface BookingClickListener {
        fun onBookingClick(createBooking: CreateBooking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_desks, parent, false)
        return DeskViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeskViewHolder, position: Int) {
        val desk = getItem(position)
        holder.bind(desk)
    }

    inner class DeskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deskLabel: TextView = itemView.findViewById(R.id.tvDeskLabel)
        private val officeName: TextView = itemView.findViewById(R.id.tvOfficeName)
        private val btnReserve: Button = itemView.findViewById(R.id.btnDeskReserve)

        init {
            btnReserve.setOnClickListener {
                val desk = getItem(adapterPosition)
                val createBooking = CreateBooking(dateStart = "", dateEnd = "", desk = desk.id)
                bookingClickListener.onBookingClick(createBooking)
                Toast.makeText(itemView.context, "Reserve button clicked", Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(desk: Desk) {
            deskLabel.text = desk.label
            officeName.text = desk.office.name
            desk.id
        }
    }

    class DeskDiffCallback : DiffUtil.ItemCallback<Desk>() {
        override fun areItemsTheSame(oldItem: Desk, newItem: Desk): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Desk, newItem: Desk): Boolean {
            return oldItem == newItem
        }
    }

}

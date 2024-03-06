package com.codingschool.deskbooking.ui.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.authentication.bookings.BookingResponse

class ReservationAdapter :
    ListAdapter<BookingResponse, ReservationAdapter.ReservationViewHolder>(BookingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val booking = getItem(position)
        holder.bind(booking)
    }

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deskLabel: TextView = itemView.findViewById(R.id.tvReservationDesk)
        private val dateStart: TextView = itemView.findViewById(R.id.tvDateStart)
        private val dateEnd: TextView = itemView.findViewById(R.id.tvDateEnd)

        fun bind(booking: BookingResponse) {
            deskLabel.text = booking.desk.label
            dateStart.text = booking.dateStart
            dateEnd.text = booking.dateEnd
        }
    }

    class BookingDiffCallback : DiffUtil.ItemCallback<BookingResponse>() {
        override fun areItemsTheSame(oldItem: BookingResponse, newItem: BookingResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BookingResponse,
            newItem: BookingResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}
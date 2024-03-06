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
import com.codingschool.deskbooking.data.model.authentication.equipment.Equipment

class DesksAdapter(
    private val bookingClickListener: BookingClickListener,
    private var equipmentList: List<Equipment> = emptyList()

) : ListAdapter<Desk, DesksAdapter.DeskViewHolder>(DeskDiffCallback()) {

    interface BookingClickListener {
        fun onBookingClick(createBooking: CreateBooking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_desks, parent, false)
        return DeskViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeskViewHolder, position: Int) {
        val desk = getItem(position)
        val equipment = equipmentList.getOrNull(position)
        holder.bind(desk, equipment)
    }


    inner class DeskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deskLabel: TextView = itemView.findViewById(R.id.tvDeskLabel)
        private val officeName: TextView = itemView.findViewById(R.id.tvOfficeName)
        private val btnReserve: Button = itemView.findViewById(R.id.btnDeskReserve)
        private val equipmentName: TextView = itemView.findViewById(R.id.tvEquipmentName)

        init {
            btnReserve.setOnClickListener {
                val desk = getItem(adapterPosition)
                val createBooking = CreateBooking(dateStart = "", dateEnd = "", desk = desk.id)
                bookingClickListener.onBookingClick(createBooking)
                Toast.makeText(itemView.context, "Reserve button clicked", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        fun bind(desk: Desk, equipment: Equipment?) {
            deskLabel.text = desk.label
            officeName.text = desk.office.name
            desk.id
            if (equipmentList.isNullOrEmpty()) {
                equipmentName.text = "No Equipment"
            } else {
                val equipmentForDesk = equipmentList.find { it.id == desk.id }
                if (equipmentForDesk != null) {
                    equipmentName.text = equipmentForDesk.name
                } else {
                    equipmentName.text = "No Equipment"
                }
            }
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

    fun updateEquipment(newEquipmentList: List<Equipment>) {
        equipmentList = newEquipmentList
        notifyDataSetChanged()
    }
}

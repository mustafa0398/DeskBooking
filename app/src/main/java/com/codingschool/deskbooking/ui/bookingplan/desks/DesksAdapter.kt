package com.codingschool.deskbooking.ui.bookingplan.desks

import android.app.DatePickerDialog
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
import com.codingschool.deskbooking.data.model.dto.bookings.CreateBooking
import com.codingschool.deskbooking.data.model.dto.desks.Desk
import com.codingschool.deskbooking.data.model.dto.equipment.Equipment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


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
        private val btnStartDate: Button = itemView.findViewById(R.id.btnStartDate)
        private val btnEndDate: Button = itemView.findViewById(R.id.btnEndDate)
        private val tvDateStart: TextView = itemView.findViewById(R.id.tvDeskDateStart)
        private val tvDateEnd: TextView = itemView.findViewById(R.id.tvDeskDateEnd)

        private val startCalendar = Calendar.getInstance()
        private val endCalendar = Calendar.getInstance()

        private val startDatePicker =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                startCalendar.set(Calendar.YEAR, year)
                startCalendar.set(Calendar.MONTH, month)
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(tvDateStart, startCalendar)
            }

        private val endDatePicker =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                endCalendar.set(Calendar.YEAR, year)
                endCalendar.set(Calendar.MONTH, month)
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(tvDateEnd, endCalendar)
            }

        init {
            btnReserve.setOnClickListener {
                val desk = getItem(adapterPosition)
                val selectedStartDate = tvDateStart.text.toString()
                val selectedEndDate = tvDateEnd.text.toString()

                val dateFormatPattern = "dd-MM-yyyy"

                val formatter = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
                val isoFormatter =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

                try {
                    val startDate = formatter.parse(selectedStartDate)
                    val endDate = formatter.parse(selectedEndDate)

                    val isoStartDate = isoFormatter.format(startDate!!)
                    val isoEndDate = isoFormatter.format(endDate!!)

                    val createBooking = CreateBooking(
                        dateStart = isoStartDate,
                        dateEnd = isoEndDate,
                        desk = desk.id
                    )
                    bookingClickListener.onBookingClick(createBooking)
                    Toast.makeText(itemView.context, "Reserve button clicked", Toast.LENGTH_SHORT)
                        .show()
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }

            btnStartDate.setOnClickListener {
                DatePickerDialog(
                    itemView.context,
                    startDatePicker,
                    startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH),
                    startCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            btnEndDate.setOnClickListener {
                DatePickerDialog(
                    itemView.context,
                    endDatePicker,
                    endCalendar.get(Calendar.YEAR),
                    endCalendar.get(Calendar.MONTH),
                    endCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
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

        private fun updateLabel(textView: TextView, calendar: Calendar) {
            val myFormat = "dd-MM-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
            textView.text = sdf.format(calendar.time)
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

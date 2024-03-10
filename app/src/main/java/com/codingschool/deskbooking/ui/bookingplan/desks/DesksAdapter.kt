package com.codingschool.deskbooking.ui.bookingplan.desks

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
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
        holder.bind(desk)
    }


    inner class DeskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deskLabel: TextView = itemView.findViewById(R.id.tvDeskLabel)
        private val officeName: TextView = itemView.findViewById(R.id.tvOfficeName)
        private val btnReserve: Button = itemView.findViewById(R.id.btnDeskReserve)
        private val etDateStart: EditText = itemView.findViewById(R.id.etDeskStartDate)
        private val etDateEnd: EditText = itemView.findViewById(R.id.etDeskEndDate)
        private val btnEquipment: ImageButton = itemView.findViewById(R.id.btnEquipment)
        private val fixOrFlex: TextView = itemView.findViewById(R.id.tvFixOrFlexDesk)
        private val ivAvailability: ImageView = itemView.findViewById(R.id.ivAvailability)

        private val startCalendar = Calendar.getInstance()
        private val endCalendar = Calendar.getInstance()

        private val startDatePicker =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                startCalendar.set(Calendar.YEAR, year)
                startCalendar.set(Calendar.MONTH, month)
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(etDateStart, startCalendar)
            }

        private val endDatePicker =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                endCalendar.set(Calendar.YEAR, year)
                endCalendar.set(Calendar.MONTH, month)
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(etDateEnd, endCalendar)
            }

        init {
            btnReserve.setOnClickListener {
                val desk = getItem(adapterPosition)
                val selectedStartDate = etDateStart.text.toString()
                val selectedEndDate = etDateEnd.text.toString()

                val dateFormatPattern = "dd.MM.yyyy"

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
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }

            etDateStart.setOnClickListener {
                DatePickerDialog(
                    itemView.context,
                    startDatePicker,
                    startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH),
                    startCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            etDateEnd.setOnClickListener {
                DatePickerDialog(
                    itemView.context,
                    endDatePicker,
                    endCalendar.get(Calendar.YEAR),
                    endCalendar.get(Calendar.MONTH),
                    endCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            btnEquipment.setOnClickListener {
                val desk = getItem(adapterPosition)
                val equipment = getEquipmentForDesk(desk.id)
                showEquipmentDialog(itemView.context, desk.equipment)
            }
        }

        fun bind(desk: Desk) {
            deskLabel.text = desk.label
            officeName.text = desk.office.name

            fixOrFlex.text = if (desk.fixdesk != null) {
                "Fix"
            } else {
                "Flex"
            }

            val isAvailable = desk.nextBooking == null
            if (isAvailable) {
                ivAvailability.visibility = View.VISIBLE
                ivAvailability.setImageResource(R.drawable.available_icon)
            } else {
                ivAvailability.visibility = View.VISIBLE
                ivAvailability.setImageResource(R.drawable.unavailable_icon)
            }
            desk.id
        }

        private fun updateLabel(textView: TextView, calendar: Calendar) {
            val myFormat = "dd.MM.yyyy"
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

    private fun getEquipmentForDesk(deskId: String): List<Equipment> {
        return equipmentList.filter { it.id == deskId }
    }

    private fun showEquipmentDialog(context: Context, equipment: List<String>) {
        val equipmentNames = equipment.map { it }.toTypedArray()

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(context.getString(R.string.equipments))
        dialogBuilder.setItems(equipmentNames) { dialog, which ->
            val selectedEquipment = equipment[which]
            dialog.dismiss()
        }
        dialogBuilder.setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }
}
